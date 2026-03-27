export default {
    props: {Plus: {type: Object, required: true},message: {type: Object, required: true}},
    template: `
     <div style="margin-bottom: 20px">
     <el-button type="success" style="width: 110px;height: 38px" @click="InsertWindowStatus=true">添加班级</el-button>
     <el-dialog v-model="InsertWindowStatus" width="260">
        <template #header>
            <div style="color: green;font-size: 19px;margin-left: 67px">添加班级</div>
        </template>
        <el-input v-model="InsertList.Name" placeholder="请输入班级名"/>
        <el-input style="margin-top: 13px;margin-bottom: 27px" v-model="InsertList.Manager" placeholder="请输入班主任名字"/>
        <el-button style="margin-left: 30px" type="success" size="default" @click="InsertClass" :loading="InsertStatus">添加</el-button>
        <el-button style="margin-left: 35px" type="info" size="default" @click="InsertWindowStatus=false">取消</el-button>
     </el-dialog>
     <el-input v-model="QueryId" placeholder="输入查询班号" style="width: 170px;height: 38px;margin-left: 345px"></el-input>
     <el-input v-model="QueryName" placeholder="输入查询班级名字" style="width: 170px;height: 38px;margin-left: 45px"></el-input>
     <el-input v-model="QueryManager" placeholder="输入查询班主任名字" style="width: 170px;height:38px;margin-left: 45px"></el-input>
     <el-button type="primary" style="width: 90px;height: 38px;margin-left: 79px" @click="QueryClass" :loading="QueryStatus">查询班级</el-button>
     </div>
     <el-table border :data="DisplayList" style="align-content: center">
        <el-table-column prop="classId" label="班号" width="60" align="center"></el-table-column>
        <el-table-column prop="className" label="班级名称" align="center"></el-table-column>
        <el-table-column prop="manager" label="班主任" align="center"></el-table-column>
        <el-table-column prop="count" label="班级人数" align="center" width="90"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
        <el-table-column prop="updateTime" label="最新更新时间" align="center"></el-table-column> 
        <el-table-column label="操作" align="center">
            <template #default="score">
                <el-button type="warning" @click="UpdateClassId(score.row)">修改</el-button>
                <el-dialog v-model="UpdateWindowStatus" width="260">
                     <template #header>
                         <div style="margin-left: 30px;font-size: 19px;color: darkorange">修改班级</div>
                     </template>
                     <el-input style="margin-bottom: 11px" v-model="UpdateList.Name" placeholder="请输入班级名"/>
                     <el-input style="margin-bottom: 11px" v-model="UpdateList.Manager" placeholder="请输入班主任名字"/>
                     <el-input style="margin-bottom: 20px" v-model="UpdateList.count" placeholder="请输入班级人数"/>
                     <el-button type="warning" @click="UpdateClass" :loading="UpdateStatus">修改班级</el-button>
                     <el-button type="info" @click="UpdateWindowStatus=false">取消</el-button>
                </el-dialog>
                <el-button style="margin-left: 25px" type="danger" @click="DeleteClassId(score.row)">删除</el-button>
                <el-dialog width="260" v-model="DeleteWindowStatus">
                     <template #header> 
                         <div align="center" style="color:red;margin-left: 22px">是否删除当前班级?</div>
                     </template> 
                     <el-button type="danger" @click="DeleteClass" :loading="DeleteStatus">删除</el-button>
                     <el-button style="margin-left: 30px" type="info" @click="DeleteWindowStatus=false">取消</el-button>
                </el-dialog>
            </template>
        </el-table-column>
     </el-table>
     <div style="display: flex; justify-content: center; margin: 30px 0;">
     <el-pagination
            :current-page="currentPage"
            :page-size="pageSize"
            :page-sizes="[5,10]"
            :disabled="disabled"
            :background="background"
            layout="total,sizes,prev,pager,next,jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
     />
     </div>
    `,
    data() {
        return {
            sameurl:"http://r324c74e.natappfree.cc",

            currentPage:1,
            pageSize:10,
            total:20,
            background:true,

            classlist:[
                {
                    classId:1,
                    className:"107班",
                    classManagerTeacher:"张三",
                    classStudentsCount:40,
                    classCreatedTime:"2021-05-01 10:00:00",
                    classLastUpdatedTime:"2021-05-01 10:00:00"
                },
                {
                    classId:2,
                    className:"107班",
                    classManagerTeacher:"张三",
                    classStudentsCount:40,
                    classCreatedTime:"2021-05-01 10:00:00",
                    classLastUpdatedTime:"2021-05-01 10:00:00"
                },
                {
                    classId:3,
                    className:"107班",
                    classManagerTeacher:"张三",
                    classStudentsCount:40,
                    classCreatedTime:"2021-05-01 10:00:00",
                    classLastUpdatedTime:"2021-05-01 10:00:00"
                },
                {
                    classId:4,
                    className:"107班",
                    classManagerTeacher:"张三",
                    classStudentsCount:40,
                    classCreatedTime:"2021-05-01 10:00:00",
                    classLastUpdatedTime:"2021-05-01 10:00:00"
                }
            ],
            DisplayList:[],
            QueryId:'',
            QueryName:'',
            QueryManager:'',
            QueryStatus:false,

            InsertList:{
                Name:'',
                Manager:''
            },
            InsertWindowStatus:false,
            InsertStatus:false,

            UpdateId:'',
            UpdateList:{
                Name:'',
                Manager:'',
                count:'',
            },
            UpdateWindowStatus:false,
            UpdateStatus:false,

            DeleteId:'',
            DeleteWindowStatus:false,
            DeleteStatus:false
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
        sleep:(time)=>{return new Promise(resolve => setTimeout(resolve, time))},
        async GetPageSizeClass(page,size){
            await this.ValidStatus();
            this.DisplayList=[];
            const startIndex = (page-1)*size;
            const nlen = Math.min(size,this.classlist.length-startIndex);
            console.log(nlen+" "+this.classlist.length);
            console.log(this.classlist);
            for(let i = 0;i<nlen;i++){
                this.DisplayList[i]=this.classlist[startIndex+i];
                console.log(this.DisplayList[i]+" "+this.classlist[startIndex+i]);
            }
        },
        handleSizeChange(currentsize){
            this.pageSize=currentsize;
            this.GetPageSizeClass(this.currentPage,this.pageSize);
        },
        handleCurrentChange(currentpage){
            this.currentPage=currentpage;
            this.GetPageSizeClass(this.currentPage,this.pageSize);
        },
        async InsertClass(){
            this.ValidStatus();
            const Mlen = this.InsertList.Manager.length,Nlen = this.InsertList.Name.length;
            if(Mlen<=0 || Mlen>10){
                this.$message.error("班主任名字长度限制为10个字符");
                return;
            }
            if(Nlen<=0 || Nlen>10){
                this.$message.error("班级名长度限制为10个字符");
                return;
            }
            this.InsertStatus=true;
            const result = await axios.post(this.sameurl+"/AddClass",null,{
                "params":{
                    "name":this.InsertList.Name,
                    "teachername":this.InsertList.Manager
                }
            });
            if(result.data.code===200){console.log(result);this.$message.success("添加成功");}
            else{console.log(result);this.$message.error(result.data.message)}
            this.InsertList.Name='';
            this.InsertList.Manager='';
            this.InsertStatus=false;
            this.InsertWindowStatus=false;
        },
        UpdateClassId(row){
            this.UpdateWindowStatus=true;
            this.UpdateId=row.classId;
        },
        DeleteClassId(row){
            this.DeleteId=row.classId;
            this.DeleteWindowStatus=true;
        },
        async LoadingList(){
            await this.ValidStatus();
            try {
                const result = await axios.get(this.sameurl+"/GetAllClass"),Ntotal = await axios.get(this.sameurl+"/QueryClassCount");
                if (result.data.code === 200) {
                    this.classlist = result.data.data;
                    this.total=Ntotal.data.data;
                    const nlen = Math.min(this.classlist.length,10);
                    for(let i =0;i<nlen;i++){this.DisplayList[i]=this.classlist[i];}
                }
            }catch(e){
                console.log(e);
                this.$message.warning("服务器繁忙,请稍后重试");
            }
        },
        async DeleteClass(){
            await this.ValidStatus();
            this.DeleteStatus=true;
            await this.sleep(1000)
            const result = await axios.delete(this.sameurl+"/DelClass",{params:{"classId":this.DeleteId}});
            if(result.data.code===200){
                this.$message.success("删除成功");
                this.LoadingList();
                this.currentPage=1;
            }
            else{this.$message.error("删除失败");}
            this.DeleteStatus=false;
            this.DeleteWindowStatus=false;
            this.DeleteId='';
        },
        async UpdateClass(){
            await this.sleep(1000);
            const Nlen = this.UpdateList.Name.length,mlen = this.UpdateList.Manager.length;
            if(Nlen<=0 || Nlen>10){
                this.$message.warning("班级名长度应在10字符以内");
                return;
            }
            if(mlen<=0 || mlen>10){
                this.$message.warning("班主任名字长度应在10字符以内");
                return;
            }
            this.UpdateStatus=true;
            const result = await axios.put(this.sameurl+"/PutClass",null,{
                params: {
                    "classId":this.UpdateId,
                    "name":this.UpdateList.Name,
                    "count":this.UpdateList.count,
                    "manager":this.UpdateList.Manager
                }
            });
            if(result.data.code===200){
                this.$message.success("修改成功");
                this.LoadingList();
                this.currentPage=1;
            }
            else{this.$message.error("删除失败");}
            this.UpdateStatus=false;
            this.UpdateList.Name='';
            this.UpdateList.Manager='';
            this.UpdateList.count='';
            this.UpdateWindowStatus=false;
        },
        async QueryClass(){
            this.QueryStatus=true;
            await this.sleep(500);
            const result = await axios.get(this.sameurl+"/ValueQuery",{
                params:{
                    "classId":this.QueryId===''?0:this.QueryId,
                    "name":this.QueryName,
                    "manager":this.QueryManager
                }});
            if(result.data.code===200){
                this.DisplayList=[];
                this.classlist = result.data.data;
                this.total=this.classlist.length;
                const nlen = Math.min(this.classlist.length,10);
                for(let i =0;i<nlen;i++){this.DisplayList[i]=this.classlist[i];}
                this.currentPage=1;
            }
            this.QueryStatus=false;
        },
    },
    mounted(){
        this.LoadingList();
    }
}