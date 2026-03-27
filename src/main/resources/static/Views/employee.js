export default{
    props: {Plus: {type: Object, required: true}, message: {type: Object, required: true},ElLoading:{type: Object, required: true}},
    template:`
        <div>
            <div style="margin-bottom: 20px;">
            <el-button type="success" style="width: 110px;height:38px" @click="addemp = true" >添加员工</el-button>
            <el-dialog v-model="addemp" width="529px">
               <el-form style="margin-top: 10px;">
                  <el-row :gutter="28">
                     <el-col :span="8">
                        <el-form-item label="姓名">
                           <el-input v-model="addempname"/>
                        </el-form-item>
                     </el-col>
                     <el-col :span="16">
                        <el-form-item label="手机号">
                           <el-input v-model="addempphone"/>
                        </el-form-item>
                     </el-col>
                  </el-row>
                  <el-row :gutter="28" style="margin-top: 10px">
                     <el-col :span="8">
                        <el-form-item label="职位">
                        <el-input v-model="addempposition">
                        </el-form-item>
                     </el-col>
                     <el-col :span="8">
                        <el-form-item label="所属部门">
                        <el-input v-model="addempdept">
                        </el-form-item>
                     </el-col>
                     <el-col :span="8">
                        <el-form-item label="性别">
                           <el-select v-model="addempgender" placeholder="选择性别">
                              <el-option label="男" value="男"/>
                              <el-option label="女" value="女"/>
                           </el-select>
                        </el-form-item>
                     </el-col>
                  </el-row>
                  <el-row style="margin-top: 10px">
                     <el-col :span="22">
                        <el-form-item label="头像">
                           <el-upload
                               ref="UploadRef"
                               :http-request="UploadSubmit"
                               :on-change="HandlerChangeUploadFile"
                               :on-exceed="HandlerExceedUploadFile"
                               list-type="picture-card"
                               :auto-upload="false"
                               limit="1"
                               size="30"
                           >
                           <el-icon><component :is="Plus"/></el-icon>
                           </el-upload>
                        </el-form-item>   
                     </el-col>
                  </el-row>
                  <el-row>
                     <el-col :span="7"></el-col>
                     <el-col :span="6">
                     <el-button type="success" size="default" @click="HandlerUploadForm" :loading="uploadLoading">提交</el-button>
                     </el-col>
                     <el-col :span="2"></el-col>
                     <el-col :span="6">
                     <el-button type="info" size="default" @click="addemp=false">取消</el-button>
                     </el-col>
                     <el-col :span="7"></el-col>
                  </el-row>
               </el-form>
            </el-dialog>
            <el-input placeholder="请输入员工ID" style="width: 150px;height: 38px;margin-left: 118px;" v-model="QueryId"></el-input>
            <el-input placeholder="请输入员工姓名" style="width: 150px;height: 38px;margin-left: 15px;" v-model="QueryName"></el-input>
            <el-input placeholder="请输入员工手机号" style="width: 150px;height: 38px;margin-left: 15px;" v-model="QueryPhone"></el-input>
            <el-input placeholder="请输入员工所属部门" style="width: 150px;height: 38px;margin-left: 15px" v-model="QueryDept"></el-input>
            <el-input placeholder="请输入职位" style="width:150px;height:38px;margin-left: 15px" v-model="QueryPosition"></el-input>
            <el-button type="primary" style="width:110px;height: 38px;margin-left: 78px" @click="QueryEmp" :loading="queryLoading">查询员工</el-button>
            </div>
            <el-table :data="employeelist" border >
                <el-table-column prop="id" label="员工ID" align="center"></el-table-column>
                <el-table-column prop="headImg" label="员工头像" align="center">
                   <template #default="scope">
                      <el-avatar :size="60" :src="scope.row.headImg" @error="handleAvatarError(scope)" lazy>
                         <img :src="defaultAvatar"/>
                      </el-avatar>
                   </template>
                </el-table-column>   
                <el-table-column prop="name" label="姓名" align="center"></el-table-column>
                <el-table-column prop="gender" label="性别" align="center"></el-table-column>
                <el-table-column prop="phone" label="手机号" align="center" width="120"></el-table-column>
                <el-table-column prop="deptName" label="所属部门" align="center"></el-table-column>
                <el-table-column prop="position" label="职位" align="center"></el-table-column>
                <el-table-column prop="createTime" label="入职时间" align="center"></el-table-column>
                <el-table-column prop="updateTime" label="修改时间" align="center"></el-table-column>
                <el-table-column label="操作" align="center" width="230">
                <template #default="scope">
                <div style="display: flex; justify-content: center;">
                   <el-button type="warning" @click="PutEmp(scope)">修改</el-button>
                   <el-dialog v-model="putemp" width="529px">
                      <el-form style="margin-top: 10px;">
                         <el-row :gutter="28">
                            <el-col :span="8">
                               <el-form-item label="姓名">
                                  <el-input v-model="putempname"/>
                               </el-form-item>
                            </el-col>
                            <el-col :span="16">
                               <el-form-item label="手机号">
                                  <el-input v-model="putempphone"/>
                               </el-form-item>
                            </el-col>
                         </el-row>
                         <el-row :gutter="28" style="margin-top: 10px">
                            <el-col :span="8">
                               <el-form-item label="职位">
                               <el-input v-model="putempposition">
                               </el-form-item>
                            </el-col>
                            <el-col :span="8">
                               <el-form-item label="所属部门">
                               <el-input v-model="putempdept">
                               </el-form-item>
                            </el-col>
                            <el-col :span="8">
                               <el-form-item label="性别">
                                  <el-select v-model="putempgender" placeholder="选择性别">
                                     <el-option label="男" value="男"/>
                                     <el-option label="女" value="女"/>
                                  </el-select>
                               </el-form-item>
                            </el-col>
                         </el-row>
                         <el-row style="margin-top: 10px">
                            <el-col :span="22">
                               <el-form-item label="头像">
                                  <el-upload
                                      ref="PutUploadRef"
                                      :http-request="PutUploadSubmit"
                                      :on-change="PutHandlerChangeUploadFile"
                                      :on-exceed="PutHandlerExceedUploadFile"
                                      list-type="picture-card"
                                      :auto-upload="false"
                                      limit="1"
                                      size="30"
                                  >
                                  <el-icon><component :is="Plus"/></el-icon>
                                  </el-upload>
                               </el-form-item>   
                            </el-col>
                         </el-row>
                         <el-row>
                            <el-col :span="7"></el-col>
                            <el-col :span="6">
                            <el-button type="success" size="default" @click="PutHandlerUploadForm" :loading="putuploadLoading">提交</el-button>
                            </el-col>
                            <el-col :span="2"></el-col>
                            <el-col :span="6">
                            <el-button type="info" size="default" @click="putemp=false">取消</el-button>
                            </el-col>
                            <el-col :span="7"></el-col>
                         </el-row>
                      </el-form>
                   </el-dialog>
                   <el-button type="danger" style="margin-left: 30px" @click="DropEmp(scope)" :loading="dropLoading">删除</el-button>
                   <el-dialog v-model="dropemp" title="是否删除当前员工" style="width: 280px" align="center">
                      <el-button type="primary" @click="DeleteEmp">确认</el-button>
                      <el-button type="info" @click="dropemp=false">取消</el-button>
                   </el-dialog>
                </div>
                </template>
                </el-table-column>
            </el-table>
            <div style="display: flex; justify-content: center; margin: 30px 0;">
            <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[3,6]"
            :disabled="disabled"
            :background="background"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            />
            </div>
        </div>
   `,
    data(){
        return {
            employeelist: [
                {
                    id: 1,
                    headImg:'https://tlias-manager-system-house.oss-cn-beijing.aliyuncs.com/001.jpg',
                    name: '张三',
                    gender: '男',
                    phone:"12354365656",
                    deptId:"0",
                    deptName: '行政部',
                    position: '行政部部长',
                    createTime: '2021-01-01 06:40:44',
                    updateTime: '2021-01-01 06:40:44'
                }
            ],
            addemp:false,
            addempname:'',
            addempphone:'',
            addempposition:'',
            addempdept:'',
            addempgender:'男',
            addempheadimg:'',
            UploadFile:null,
            UploadRef:null,
            FileName:'',
            uploadLoading:false,

            putemp:false,
            putempname:'',
            putempphone:'',
            putempposition:'',
            putempdept:'',
            putempgender:'男',
            putempheadimg:'',
            PutUploadFile:null,
            PutUploadRef:null,
            PutFileName:'',
            putuploadLoading:false,

            QueryId:'',
            QueryName:'',
            QueryPhone:'',
            QueryDept:'',
            QueryPosition:'',
            queryLoading:false,

            dropemp:false,
            dropid:'',
            dropLoading:false,

            sameurl:"http://r324c74e.natappfree.cc",
            defaultAvatar:"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",//120*120
            errorHandler:false,
            currentPage:1,
            pageSize:6,
            background:true,
            disabled:false,
            total:0,
        }
    },
    methods:{
        async ValidStatus(){
            const naccountFlag=localStorage.getItem("accountFlag"),ntoken = localStorage.getItem("token");
            try{
                const result = await axios.get(this.sameurl+"/health",{headers:{"accountFlag":naccountFlag,"token":ntoken}});
            }catch(e){
                console.log(e);
                if(e.response.status){
                    this.$message.error(e.response.data);
                    localStorage.removeItem("accountFlag");
                    localStorage.removeItem("token");
                    await this.sleep(1000);
                    window.location.href="/login.html";
                }
            }
        },
        sleep(ms) {return new Promise(resolve => setTimeout(resolve, ms));},//等待函数
        async QueryEmp() {
            await this.ValidStatus();
            this.queryLoading=true;
            await this.sleep(500);
            try {
                axios.get(this.sameurl + "/QueryEmp", {
                    params: {
                        "id": this.QueryId === '' ? 0 : this.QueryId,
                        "name": this.QueryName,
                        "deptId": this.QueryDept,
                        "position": this.QueryPosition,
                        "phone": this.QueryPhone,
                        "page": this.currentPage,
                        "count": this.total,
                        "size": this.pageSize
                    }
                }).then((result) => {
                    if (result.data.code !== 200) {
                        this.QueryId = '';
                        this.QueryName = '';
                        this.QueryDept = '';
                        this.QueryPosition = '';
                        this.$message.error("服务器错误,请稍后重试");
                    } else {
                        this.$message.success('查询成功')
                        this.employeelist=result.data.data;
                    }
                })
            }catch(e){
                this.$message.warn("请检查输入格式是否正确")
            }
            this.queryLoading=false;
        },
        //添加员工
        async UploadSubmit(options){
            await this.ValidStatus();
            const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif'];
            if (!allowedTypes.includes(options.file.type)) {
                this.$message.error('只支持 JPG、PNG、GIF 格式的图片！');
                this.$refs.UploadRef.clearFiles();
                return;
            }
            this.uploadLoading=true;
            const formData = new FormData();
            formData.append("name",this.addempname);
            formData.append("phone",this.addempphone);
            formData.append("position",this.addempposition===''?'无':this.addempposition);
            formData.append("deptId",this.addempdept);
            formData.append("gender",this.addempgender);
            formData.append("UploadFile",options.file);
            await axios.post(this.sameurl+"/InsertEmp",formData,{headers:{"Content-Type":"multipart/form-data"}}).then(
                (result)=>{
                    if(result.data.code===200){this.$message.success(result.data.data);}
                    else{this.$message.error(result.data.message);}
                    this.addempname='';
                    this.addempphone='';
                    this.addempposition='';
                    this.addempdept='';
                    this.addempgender='男';
                    this.$refs.UploadRef.clearFiles();
                }
            );
            this.uploadLoading=false;
            this.GetPageSizeEmp(1,this.pageSize);
            this.addemp=false;
        },
        HandlerExceedUploadFile(startFiles){
            const sfile = startFiles[0];
            this.$refs.UploadRef.clearFiles();
            this.$refs.UploadRef.handleStart(sfile);
            this.FileName=sfile.name;
            this.UploadFile=sfile;
        },
        HandlerChangeUploadFile(files){
            this.FileName=files.name;
            this.UploadFile=files;
        },
        HandlerUploadForm(file){
            if(this.addempphone.length!==11){
                this.$message.error("手机号长度必须为11位")
                return;
            }
            if(this.addempname===''){
                this.$message.error("请输入姓名");
                return;
            }
            if(this.FileName.length>10){
                this.$message.error("文件名长度最长为10");
                return;
            }
            if(this.$refs.UploadRef && this.UploadFile){this.$refs.UploadRef.submit();}else{window.alert("请先上传文件")}
        },

        //修改员工
        async PutUploadSubmit(options){
            await this.ValidStatus();
            const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif'];
            if (!allowedTypes.includes(options.file.type)) {
                this.$message.error('只支持 JPG、PNG、GIF 格式的图片！');
                this.$refs.PutUploadRef.clearFiles();
                return;
            }
            this.putuploadLoading=true;
            const formData = new FormData();
            formData.append("name",this.putempname);
            formData.append("phone",this.putempphone);
            formData.append("position",this.putempposition===''?'无':this.putempposition);
            formData.append("deptId",this.putempdept);
            formData.append("gender",this.putempgender);
            formData.append("UploadFile",options.file);
            formData.append("id",this.putid);
            await axios.put(this.sameurl+"/UpdateEmp",formData,{headers:{"Content-Type":"multipart/form-data"}}).then(
                (result)=>{
                    if(result.data.code===200){this.$message.success(result.data.data);}
                    else{this.$message.error(result.data.message);}
                    this.putempname='';
                    this.putempphone='';
                    this.putempposition='';
                    this.putempdept='';
                    this.putempgender='男';
                    this.putid='';
                    this.$refs.PutUploadRef.clearFiles();
                }
            );
            this.putuploadLoading=false;
            this.putemp=false;
        },
        PutHandlerExceedUploadFile(startFiles){
            const sfile = startFiles[0];
            this.$refs.PutUploadRef.clearFiles();
            this.$refs.PutUploadRef.handleStart(sfile);
            this.PutFileName=sfile.name;
            this.PutUploadFile=sfile;
        },
        PutHandlerChangeUploadFile(files){
            this.PutFileName=files.name;
            this.PutUploadFile=files;
        },
        PutHandlerUploadForm(file){
            if(this.putempphone.length!==11){
                this.$message.error("手机号长度必须为11位")
                return;
            }
            if(this.putempname===''){
                this.$message.error("请输入姓名");
                return;
            }
            if(this.PutFileName.length>10){
                this.$message.error("文件名长度最长为10");
                return;
            }
            if(this.$refs.PutUploadRef && this.PutUploadFile){this.$refs.PutUploadRef.submit();}else{window.alert("请先上传文件")}
        },


        DropEmp(scope){
            this.dropid=scope.row.id;
            this.dropemp=true;
        },
        PutEmp(scope){
            this.putemp=true;
            this.putid=scope.row.id;
        },
        handleAvatarError(scope){
            console.log(scope.row.headImg)
            scope.row.headImg=this.defaultAvatar;
            return true;
        },
        handleSizeChange(){this.GetPageSizeEmp();},
        handleCurrentChange(){this.GetPageSizeEmp();},
        GetAllEmpCount(){axios.get(this.sameurl+"/GetAllEmpCount").then((result)=>{this.total=result.data.data;})},
        async GetPageSizeEmp() {
            await this.ValidStatus();
            this.GetAllEmpCount();
            axios.get(this.sameurl + "/GetPageSizeEmp", {params: {"page":this.currentPage,"size":this.pageSize}}).then((result) => {this.employeelist = result.data.data;});
        },
        async DeleteEmp(){
            await this.ValidStatus();
            this.dropLoading=true;
            this.dropemp=false;
            await this.sleep(500);
            axios.delete(this.sameurl+"/DeleteEmp",{params:{"id":this.dropid}}).then((result)=>{
                this.GetPageSizeEmp(1,this.pageSize);
                window.alert(result.data.message);
            });
            this.dropLoading=false;
        },
    },
    mounted(){
        this.GetPageSizeEmp();
    }
}