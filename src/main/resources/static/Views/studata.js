export default {
    props:{"Plus":Plus,"message": ElMessage},
    template: `
        <div style="padding: 20px;">
            <div id="ageChart" style="width: 100%; height: 300px;"></div>
        </div>
        <div style="padding: 20px;">
            <div id="genderChart" style="width: 100%;height: 300px"></div>
        </div>
    `,
    data() {
        return {
            sameurl:"http://r324c74e.natappfree.cc",
            chart: null,
            chartOption: {
                title: {
                    show: true,
                    text: "Tlias智能辅助系统员工学生曲线图",
                    left: 'center'
                },
                tooltip: {
                    show: true,
                    trigger: "axis"
                },
                xAxis: {
                    type: "category",
                    data: ["20~35岁", "35~40岁", "40~50岁", "50~60岁", "60~70岁"]
                },
                yAxis: {
                    type: "value",
                    name: "人数"
                },
                series: [
                    {
                        type: "line",
                        name: "年龄",
                        data: [30, 20, 10, 25, 35],
                        label: {
                            show: true
                        },
                        smooth: true
                    }
                ],
                legend: {
                    data: ["员工年龄"],
                    show: true
                }
            },
            chart1:null,
            chartOption1:{
                title: {
                    show: true,
                    text: "Tlias智能辅助系统员工学生柱状图",
                    left: 'center',
                },
                tooltip: {
                    show: true,
                    trigger: "axis"
                },
                xAxis: {
                    type: 'category',
                    data: ['男','女']
                },
                yAxis: {
                    type: 'value',
                    name:"人数"
                },
                series: [
                    {
                        data: [],
                        type: 'bar',
                        name:"员工性别",
                        label:{
                            show:true
                        }
                    }
                ]
            }
        }
    },
    mounted() {
        this.ValidStatus();
        this.GetGenderCount();
        this.$nextTick(() => {
            this.initChart();
        });
        setInterval(() => {
            this.updateChart();
        }, 30000);
    },
    methods: {
        sleep(ms){return new Promise(resolve=>setTimeout(resolve,ms));},
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
        initChart() {
            const chartDom = document.getElementById('ageChart');
            const chartDom1 = document.getElementById('genderChart');
            if (!chartDom) return;
            if (!chartDom) return;
            this.chart = echarts.init(chartDom);
            this.chart1 = echarts.init(chartDom1);
            this.chart.setOption(this.chartOption);
            this.chart1.setOption(this.chartOption1);
            window.addEventListener('resize', () => {
                if (this.chart) {this.chart.resize();}
            });
            window.addEventListener('resize',()=> {
               if(this.chart1){this.chart1.resize();}
            });
        },
        async updateChart() {
            await this.ValidStatus();
            this.GetGenderCount();
        },
        GetGenderCount(){
            axios.get(this.sameurl+"/Stu/GetGenderCount").then((res)=>{
                if(typeof res.data.data==="string"){
                    res.data.data=JSON.parse(res.data.data)
                }
                if(res.data.code===200){
                    const newData = [0,0];
                    console.log(res.data.data);
                    for(let i =0;i<res.data.data.length;i++){
                        if(res.data.data[i].gender==="男"){
                            newData[0]=res.data.data[i].count;
                        }else{
                            newData[1]=res.data.data[i].count;
                        }
                    }
                    this.chartOption1.series[0].data = newData;
                    this.chart1.setOption(this.chartOption1);
                }else{
                    this.$message.warning("网络加载不稳定,数据加载异常");
                    console.log("网络加载不稳定,数据加载异常");
                }
            });
        }
    },
    beforeUnmount() {
        if (this.chart) {this.chart.dispose();}
    }
}
