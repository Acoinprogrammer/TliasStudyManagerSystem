export default{
    props: {Plus: {type: Object, required: true}},
    template:`
       <div style="margin-bottom: 20px">
          <el-button @click="AddWindowStatus=true" style="width: 110px;height:38px" type="success">添加学生</el-button>
          <el-dialog v-model="AddWindowStatus" width="529px">
               <el-form style="margin-top: 10px;">
                  <el-row :gutter="28">
                     <el-col :span="8">
                        <el-form-item label="姓名">
                           <el-input v-model="AddName"/>
                        </el-form-item>
                     </el-col>
                     <el-col :span="16">
                        <el-form-item label="所属班级">
                           <el-input v-model="AddClassName"/>
                        </el-form-item>
                     </el-col>
                  </el-row>
                  <el-row :gutter="28" style="margin-top: 10px">
                     <el-col :span="8">
                        <el-form-item label="年龄">
                        <el-input v-model="AddAge">
                        </el-form-item>
                     </el-col>
                     <el-col :span="8">
                        <el-form-item label="班主任">
                        <el-input v-model="AddTeacherName">
                        </el-form-item>
                     </el-col>
                     <el-col :span="8">
                        <el-form-item label="性别">
                           <el-select v-model="AddGender" placeholder="选择性别">
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
                               ref="AddUploadRef"
                               :http-request="AddUploadSubmit"
                               :on-change="AddHandlerChangeUploadFile"
                               :on-exceed="AddHandlerExceedUploadFile"
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
                     <el-button type="success" size="default" @click="AddHandlerUploadForm" :loading="AddUploadLoading">提交</el-button>
                     </el-col>
                     <el-col :span="2"></el-col>
                     <el-col :span="6">
                     <el-button type="info" size="default" @click="AddWindowStatus=false">取消</el-button>
                     </el-col>
                     <el-col :span="7"></el-col>
                  </el-row>
               </el-form>
          </el-dialog>
          <el-input v-model="QueryId" style="width: 160px;height: 38px;margin-left: 155px" placeholder="请输入查询的学生学号"/>
          <el-input v-model="QueryName" style="width: 160px;height: 38px;margin-left: 40px" placeholder="请输入查询的学生姓名"/>
          <el-input v-model="QueryClass" style="width: 160px;height: 38px;margin-left:40px" placeholder="所属班级"/>
          <el-input v-model="QueryManager" style="width: 160px;height: 38px;margin-left: 40px" placeholder="班主任"/>
          <el-button @click="UnionQueryStudent" :loading="QueryStatus" style="width:110px;height: 38px;margin-left: 91px" type="primary">查询学生</el-button>
       </div>
       <!-- studentsList showlist -->
       <el-table border :data="showlist" >
           <el-table-column width="70" prop="id" align="center" label="学号"/>
           <el-table-column align="center" label="头像">
               <template #default="scope">
                      <el-avatar :size="60" :src="scope.row.headImage" @error="handleAvatarError(scope)" lazy>
                      <img :src="defaultAvatar"/>
                      </el-avatar>
               </template>
           </el-table-column>
           <el-table-column prop="name" align="center" label="姓名"/>
           <el-table-column width="55" prop="gender" align="center" label="性别"/>
           <el-table-column width="58" prop="age" align="center" label="年龄"/>
           <el-table-column prop="fromClass" align="center" label="所属班级"/>
           <el-table-column prop="manager" align="center" label="班主任"/>
           <el-table-column width="98" prop="createTime" align="center" label="入学时间"/>
           <el-table-column width="98" prop="graduationTime" align="center" label="毕业时间"/>
           <el-table-column width="98" prop="updateTime" align="center" label="最新更改时间"/>
           <el-table-column width="200" align="center" label="操作">
               <template #default="scope">
                   <el-button @click="PutWindow(scope.row)" type="warning">修改</el-button>
                   <el-dialog v-model="PutWindowStatus" width="529px">
                        <el-form style="margin-top: 10px;">
                           <el-row :gutter="28">
                              <el-col :span="8">
                                 <el-form-item label="姓名">
                                    <el-input v-model="PutName"/>
                                 </el-form-item>
                              </el-col>
                              <el-col :span="16">
                                 <el-form-item label="所属班级">
                                    <el-input v-model="PutClassName"/>
                                 </el-form-item>
                              </el-col>
                           </el-row>
                           <el-row :gutter="28" style="margin-top: 10px">
                              <el-col :span="8">
                                 <el-form-item label="年龄">
                                 <el-input v-model="PutAge">
                                 </el-form-item>
                              </el-col>
                              <el-col :span="8">
                                 <el-form-item label="班主任">
                                 <el-input v-model="PutTeacherName">
                                 </el-form-item>
                              </el-col>
                              <el-col :span="8">
                                 <el-form-item label="性别">
                                    <el-select v-model="PutGender" placeholder="选择性别">
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
                              <el-button type="success" size="default" @click="PutHandlerUploadForm" :loading="PutUploadLoading">提交</el-button>
                              </el-col>
                              <el-col :span="2"></el-col>
                              <el-col :span="6">
                              <el-button type="info" size="default" @click="PutWindowStatus=false">取消</el-button>
                              </el-col>
                              <el-col :span="7"></el-col>
                           </el-row>
                        </el-form>
                   </el-dialog>
                   <el-button @click="DelWindowStudent(scope.row)" style="margin-left: 20px" type="danger">删除</el-button>
                   <el-dialog width="260" v-model="DelWindowStatus">
                       <template #header>
                           <div align="center" style="color:red;margin-left: 22px">是否删除当前学生?</div>
                       </template>
                       <el-button type="danger" :loading="DelStatus" @click="DelStudent">删除</el-button>
                       <el-button style="margin-left: 30px" type="info" @click="DelWindowStatus=false">取消</el-button>
                   </el-dialog>
               </template>
           </el-table-column>
       </el-table>
       <div style="display: flex; justify-content: center; margin: 30px 0;">
            <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[3,5]"
            :disabled="disabled"
            :background="background"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            />
       </div>
    `,
    data(){
        return {
            sameurl:'http://r324c74e.natappfree.cc',
            currentPage:1,
            pageSize:3,
            background:true,
            total:0,
            studentsList: [
                {
                    "Id":1,
                    "headImg":'../static/Image/login/aside.webp',
                    "name":'测试姓名',
                    'gender':'男',
                    'age':18,
                    'className':'测试班级',
                    'teacherName':'测试班主任',
                    'enrollTime':'2021-01-01',
                    'graduationTime':'2021-01-01',
                    'updateTime':'2021-01-01'
                }
            ],
            showlist:[],
            defaultAvatar:"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",//120*120

            DelStudentId:'',
            DelWindowStatus:false,
            DelStatus:false,

            QueryId:'',
            QueryName:'',
            queryClass:'',
            QueryManager:'',
            QueryStatus:false,

            AddWindowStatus:false,
            AddUploadLoading:false,
            AddName:'',
            AddGender:'男',
            AddHeadImage:'',
            AddAge:'',
            AddClassName:'',
            AddTeacherName:'',
            AddFileName:'',
            AddUploadFile:'',

            PutWindowStatus:false,
            PutUploadLoading:false,
            PutName:'',
            PutGender:'男',
            PutHeadImage:'',
            PutAge:'',
            PutClassName:'',
            PutTeacherName:'',
            PutFileName:'',
            PutUploadFile:'',
            PutId:'',
        }
    },
    methods:{
        async ValidStatus(){
            const naccountFlag=localStorage.getItem("accountFlag"),ntoken = localStorage.getItem("token");
            try{const result = await axios.get(this.sameurl+"/health",{headers:{"accountFlag":naccountFlag,"token":ntoken}});}
            catch(e){
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
        sleep(sleepTime){return new Promise(resolve => setTimeout(resolve, sleepTime))},
        async LoadingData() {
            await this.ValidStatus();
            try{
                const result = await axios.get(this.sameurl + "/LoadingData"),count = await axios.get(this.sameurl+"/GetStudentCount");
                if(result.data.code===200){
                    this.studentsList=result.data.data;
                    this.total=count.data.data;
                    // const nlen = Math.min(3,this.studentsList.length);
                    // for(let i = 0;i<nlen;i++){this.showlist[i]=this.studentsList[i];}
                    this.ChangePageSizeStudent(1,this.pageSize);
                }
            }catch(e){
                console.log(e);
                this.$message.warning("服务器繁忙,请稍后重试");
            }
        },
        handleSizeChange(size){this.ChangePageSizeStudent(this.currentPage,this.pageSize=size);},
        handleCurrentChange(page){this.ChangePageSizeStudent(this.currentPage=page,this.pageSize);},

        handleAvatarError(scope){scope.row.headImage=this.defaultAvatar;},
        async ChangePageSizeStudent(page,size){
            this.showlist = [];
            const startIndex = (page-1)*size;
            const nlen = Math.min(size,this.studentsList.length-startIndex);
            for(let i=0;i<nlen;i++){this.showlist[i]=this.studentsList[startIndex+i];}
        },
        DelWindowStudent(row){
            this.DelStudentId = row.id;
            this.DelWindowStatus = true;
        },
        async DelStudent(){
            await this.ValidStatus();
            this.DelStatus=true;
            try {
                const result = await axios.delete("/DelStudent", {params: {"StuId": this.DelStudentId}});
                if (result.data.code === 200) {
                    this.$message.success("删除成功");
                    this.LoadingData();
                }else {
                    console.log(result);
                    this.$message.error("删除失败");
                }
            }catch(e){
                console.log(e);
                this.$message.warning("服务器繁忙,请稍后重试");
            }
            this.DelStatus=false;
            this.DelWindowStatus=false;
        },
        async UnionQueryStudent(){
            await this.ValidStatus();
            this.QueryStatus=true;
            try{
                const result = await axios.get(this.sameurl+"/UnionQueryStudent",{params:{"id":this.QueryId===''?0:this.QueryId,"name":this.QueryName,"fromClass":this.queryClass,"manager":this.QueryManager}});
                if(result.data.code===200){
                    this.studentsList=result.data.data;
                    this.total=this.studentsList.length;
                    await this.ChangePageSizeStudent(1,this.pageSize);
                }
            }catch(e){
                console.log(e);
                this.$message.warning("服务器繁忙,请稍后尝试");
            }
            this.QueryStatus=false;
        },
        //添加员工
        async AddUploadSubmit(options){
            await this.ValidStatus();
            const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif'];
            if (!allowedTypes.includes(options.file.type)) {
                this.$message.error('只支持 JPG、PNG、GIF 格式的图片！');
                this.$refs.AddUploadRef.clearFiles();
                return;
            }
            this.AddUploadLoading=true;
            const formData = new FormData();
            formData.append("name",this.AddName);
            formData.append("gender",this.AddGender);
            formData.append("manager",this.AddTeacherName);
            formData.append("age",this.AddAge);
            formData.append("fromClass",this.AddClassName);
            formData.append("AddUploadFile",options.file);
            try{
                const result = await axios.post(this.sameurl+"/AddStudent",formData,{headers:{"Content-Type":"multipart/form-data"}});
                if(result.data.code===200){this.$message.success("添加成功");}
                else{this.$message.error(result.data.message);}
            }catch(e){
                console.log("axios:post:student请求异常:"+e);
                this.$message.warning("服务器繁忙,请稍后重试");
            }finally {
                this.AddName = '';
                this.AddTeacherName = '';
                this.AddAge = '';
                this.AddClassName = '';
                this.AddGender = '男';
                this.currentPage=1;
                this.$refs.AddUploadRef.clearFiles();
                this.AddUploadLoading = false;
                await this.LoadingData();
                this.AddWindowStatus = false;
            }
        },
        AddHandlerExceedUploadFile(startFiles){
            const sfile = startFiles[0];
            this.$refs.AddUploadRef.clearFiles();
            this.$refs.AddUploadRef.handleStart(sfile);
            this.AddFileName=sfile.name;
            this.AddUploadFile=sfile;
        },
        AddHandlerChangeUploadFile(files){
            this.AddFileName=files.name;
            this.AddUploadFile=files;
        },
        AddHandlerUploadForm(file){
            const nameLen = this.AddName.length;
            const classLen = this.AddClassName.length;
            const managerLen = this.AddTeacherName.length;
            const filenameLen = this.AddFileName.length;
            const ageLen = this.AddAge.length;
            if(ageLen<=0){
                this.$message.warning("年龄不能为空且只包含数字");
                return;
            }
            if(nameLen<=0 || nameLen>10){
                this.$message.warning("姓名长度必须在10个字符以内")
                return;
            }
            if(classLen<=0 || classLen>10){
                this.$message.warning("班级名长度必须在10个字符以内");
                return;
            }
            if(managerLen<=0 || managerLen>10){
                this.$message.warning("班主任名字长度必须在10个字符以内");
                return;
            }
            if(filenameLen > 10){
                this.$message.warning("文件名长度必须在10个字符以内");
                return;
            }
            if(this.$refs.AddUploadRef && this.AddUploadFile){this.$refs.AddUploadRef.submit();}else{this.$message.warning("请先上传文件")}
        },
        PutWindow(row){
            this.PutId=row.id;
            this.PutWindowStatus = true;
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
            this.PutUploadLoading=true;
            const formData = new FormData();
            formData.append("name",this.PutName);
            formData.append("gender",this.PutGender);
            formData.append("manager",this.PutTeacherName);
            formData.append("age",this.PutAge);
            formData.append("fromClass",this.PutClassName);
            formData.append("PutUploadFile",options.file);
            formData.append("id",this.PutId);
            try{
                const result = await axios.put(this.sameurl+"/PutStudent",formData,{headers:{"Content-Type":"multipart/form-data"}});
                if(result.data.code===200){this.$message.success("修改成功");}
                else{this.$message.error(result.data.message);}
            }catch(e){
                console.log("axios:put:student请求异常:"+e);
                this.$message.warning("服务器繁忙,请稍后重试");
            }finally {
                this.PutName = '';
                this.PutTeacherName = '';
                this.PutAge = '';
                this.PutClassName = '';
                this.PutGender = '男';
                this.currentPage=1;
                this.$refs.PutUploadRef.clearFiles();
                this.PutUploadLoading = false;
                await this.LoadingData();
                this.PutWindowStatus = false;
            }
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
            const nameLen = this.PutName.length;
            const classLen = this.PutClassName.length;
            const managerLen = this.PutTeacherName.length;
            const filenameLen = this.PutFileName.length;
            const ageLen = this.PutAge.length;
            if(ageLen<=0){
                this.$message.warning("年龄不能为空且只包含数字");
                return;
            }
            if(nameLen<=0 || nameLen>10){
                this.$message.warning("姓名长度必须在10个字符以内")
                return;
            }
            if(classLen<=0 || classLen>10){
                this.$message.warning("班级名长度必须在10个字符以内");
                return;
            }
            if(managerLen<=0 || managerLen>10){
                this.$message.warning("班主任名字长度必须在10个字符以内");
                return;
            }
            if(filenameLen > 10){
                this.$message.warning("文件名长度必须在10个字符以内");
                return;
            }
            if(this.$refs.PutUploadRef && this.PutUploadFile){this.$refs.PutUploadRef.submit();}else{this.$message.warning("请先上传文件")}
        },
    },
    mounted(){
        this.LoadingData();
    }
}