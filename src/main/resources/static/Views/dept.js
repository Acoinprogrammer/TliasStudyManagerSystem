export default{
    template: '<div style="border: none">'+
        '<el-button type="success" style="margin-bottom: 20px;width: 110px;height: 38px" @click="dialogInsertDept = true">添加部门</el-button>'+
        '<el-dialog v-model="dialogInsertDept" width="240" align="center">' +
        '<el-input v-model="AddDeptName" style="width: 150px;margin: 10px 10px" size="default" placeholder="请输入部门名称"></el-input>'+
        '<div style="margin:20px 0 10px 0">'+
        '<el-button type="primary" style="margin-right:13px" @click="InsertDept">确认</el-button>'+
        '<el-button type="info" style="" @click="dialogInsertDept = false">取消</el-button>'+
        '</div>'+
        '</el-dialog>'+
        '<el-input v-model="SelectId" style="width : 170px;height : 38px;margin-bottom: 19px;margin-left: 545px" placeholder="请输入查询部门Id"></el-input>'+
        '<el-input v-model="SelectName" style="width : 170px;height : 38px;margin-bottom: 19px;margin-left: 60px" placeholder="请输入查询部门姓名"></el-input>'+
        '<el-button type="primary" style="margin-bottom: 20px;width: 90px;height: 38px;margin-left: 80px;" @click="SelectConditionQuery">查询部门</el-button>'+
        '<el-table style="width: 100%" border :data="deptlist">' +
        '<el-table-column prop="id" label="部门编号" style="width: 100%" align="center"/>'+
        '<el-table-column prop="name" label="部门名称" style="width: 100%" align="center"/>'+
        '<el-table-column prop="createTime" label="创建时间" style="width: 100%" align="center"/>'+
        '<el-table-column prop="updateTime" label="最新更新时间" style="width: 100%" align="center"/>'+
        '<el-table-column>' +
        '<template #default="scope">'+
        '<el-button type="warning" style="margin-left: 33px" @click="OpenUpdateDialog(scope.row)">修改</el-button>'+
        '<el-dialog v-model="dialogUpdateDept" width="300">' +
        '<el-input v-model="UpdateName" style="width: 160px;margin: 10px 0 10px 49px" placeholder="请输入部门名称"></el-input>'+
        '<div style="margin:14px 0 14px 0">'+
        '<el-button type="primary" style="margin-left:50px;margin-right: 30px" @click="UpdateDept">确认</el-button>'+
        '<el-button type="info" style="" @click="dialogUpdateDept = false">取消</el-button>'+
        '</div>'+
        '</el-dialog>'+
        '<el-button type="danger" style="margin-left: 33px" @click="OpenDeleteDialog(scope.row)">删除</el-button>'+
        '<el-dialog v-model="dialogDeleteDept" title="是否删除当前部门?" width="280" align="center">' +
        '<el-button type="primary" @click="confirmDeleteDept">确认</el-button>'+
        '<el-button type="info" @click="dialogDeleteDept = false">取消</el-button>'+
        '</el-dialog>'+
        '</template>'+
        '</el-table-column>'+
        '</el-table>'+
        '<div style="display: flex; justify-content: center; margin: 30px 0;">'+
        '<el-pagination' +
        '      v-model:current-page="currentPage"' +
        '      v-model:page-size="pageSize"' +
        '      :page-sizes="[5,10]"' +
        '      :disabled="disabled"' +
        '      :background="background"' +
        '      layout="total, sizes, prev, pager, next, jumper"' +
        '      :total="total"' +
        '      @size-change="handleSizeChange"' +
        '      @current-change="handleCurrentChange"' +
        '    />'+
        '</div>'+
        '</div>',
    data(){
        return {
            deptlist: [{
                id: 1,
                name: '部门1',
                createTime: '2021-01-01',
                updateTime: '2021-01-01'
            }],
            sameurl:"http://r324c74e.natappfree.cc",
            //sameurl:"http://localhost:8080",
            currentPage: 1,
            pageSize: 10,
            total: 0,
            background: true,
            disabled: false,
            dialogUpdateDept:false,
            dialogDeleteDept:false,
            SelectId:'',
            SelectName:'',
            dialogInsertDept: false,
            dialogDeleteDeptName:'',
            AddDeptName:'',
            UpdateId:'',
            UpdateName:''
        }
    },
    methods:{
        sleep(ms) {return new Promise(resolve => setTimeout(resolve, ms));},//等待函数
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
        OpenUpdateDialog(row){
            this.dialogUpdateDept=true;
            this.UpdateId=row.id;
        },
        OpenDeleteDialog(row){
            this.dialogDeleteDept=true;
            this.dialogDeleteDeptName=row.name;
        },
        async InsertDept(){
            await this.ValidStatus();
            this.dialogInsertDept=false;
            axios.post(this.sameurl+"/InsertDept",{"add_name":this.AddDeptName}).then((result)=>{
                this.AddDeptName='';
                window.alert(result.data.message);
                this.GetPageSizeDept(1,this.pageSize);
            });
        },
        async UpdateDept() {
            await this.ValidStatus();
            this.dialogUpdateDept = false;
            axios.put(this.sameurl+"/UpdateDept",{
                       "id": this.UpdateId,
                       "name": this.UpdateName
                   }
               ).then((result)=>{
                   window.alert(result.data.message);
                   this.GetPageSizeDept(1,this.pageSize);
            })
            this.UpdateName='';
        },
        handleSizeChange(){
            this.GetPageSizeDept();
        },
        handleCurrentChange(){
            this.GetPageSizeDept()
        },
        async DeleteDept(){
            await this.ValidStatus();
            axios.delete(this.sameurl+"/DeleteDept",{params:{"del_name":this.dialogDeleteDeptName}}).then((result)=>{window.alert(result.data.message);})
        },
        async confirmDeleteDept(){
            await this.ValidStatus();
            this.dialogDeleteDept=false;
            axios.delete(this.sameurl+"/DeleteDept",{params:{"del_name":this.dialogDeleteDeptName}}).then((result)=>{
                window.alert(result.data.data);
                this.GetPageSizeDept(1,this.pageSize);
            })
        },
        GetAllDept(){axios.get(this.sameurl+"/GetAllDept").then((result)=>{this.deptlist=result.data.data;})},
        GetAllDeptCount(){
            axios.get(this.sameurl+"/GetAllDeptCount").then(result=>{this.total=result.data.data;});
        },
        async GetPageSizeDept(){
            await this.ValidStatus();
            this.GetAllDeptCount();
            console.log(this.currentPage);
            console.log(this.pageSize);
            axios.get(this.sameurl+"/GetPageSizeDept",
                {
                    params:{
                        "page":this.currentPage,
                        "size":this.pageSize
                    }
                }).then(result=>{this.deptlist=result.data.data;});
        },
        async SelectConditionQuery(){
            await this.ValidStatus();
            if(this.SelectId==='' && this.SelectName===''){window.alert("请输入Id或者姓名:");}
            else{
                if(this.SelectId==='' && this.SelectName!==''){this.SelectId=0;}
                axios.get(this.sameurl+"/SelectConditionQuery",{params:{"id":this.SelectId,"name":this.SelectName}}).then((result)=>{this.deptlist=result.data.data;window.alert(result.data.message);})
            }
        }
    },
    mounted(){
        this.GetPageSizeDept(this.currentPage,this.pageSize)
    }
}