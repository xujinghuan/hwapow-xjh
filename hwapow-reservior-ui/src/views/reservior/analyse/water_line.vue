<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="月份" prop="month">
          <el-date-picker
            v-model="queryParams.params.month"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择月份" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="坝体设备" prop="senorId">
          <el-select v-model="queryParams.senorId" clearable>
            <el-option
              v-for="dict in senorOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <div class="el-card__body mid">
        <LineChart :chartData="chartData"/>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import LineChart from "@/views/dashboard/LineChart";
import {listSenor} from "@/api/reservior/senor";
import {listData} from "@/api/reservior/data";
import {parseTime} from "@/utils/hwapow";

export default {
  name: "water_line",
  components: {LineChart},
  data() {
    return {
      senorOptions: null,
      queryParams: {
        params: {month: null,getYear:null,getMonth:null},
        senorId: null,
      },
      chartData: {
        xAxisData: [],
        seriesData: [{name: "", data: []},
          {name: "", data: []},
          {name: "", data: []}],
        yAxisUnit: "米"
      }
    }
  },
  created() {
    this.initQuery();
  },
  methods: {
    initQuery() {
      listSenor({type: "0"}).then(response => {
        this.senorOptions = response.rows;
      })
    },
    getData() {
      var $this=this;
      var btData=null;//坝体数据
      var bjData=null;//坝基数据
      var kswData=null;//库位水数据
      this.loading = true;
      this.queryParams.params.order="get_time asc";
      if(this.queryParams.senorId&&this.queryParams.params.month) {
        this.queryParams.params.getYear = this.queryParams.params.month.substr(0, 4);
        this.queryParams.params.getMonth = this.queryParams.params.month.substr(5, 2);
        //获取坝体数据
        listData(this.queryParams).then(response => {
          btData = response.rows;
          //获取坝基数据
          var bjQueryParams={};
          bjQueryParams.senorId=$this.getSenorMatchSenorId($this.queryParams.senorId);
          if(!bjQueryParams.senorId){
            this.msgError("没有对应坝基设备！")
            return;
          }
          bjQueryParams.params={getYear:null,getMonth:null};
          bjQueryParams.params.getYear=$this.queryParams.params.getYear;
          bjQueryParams.params.getMonth=$this.queryParams.params.getMonth;
          listData(bjQueryParams).then(response => {
            bjData = response.rows;
            //获取库水位数据
            var kswQueryParams={};
            kswQueryParams.params={getYear:null,getMonth:null};
            kswQueryParams.params.senorType="2";
            kswQueryParams.params.getYear=$this.queryParams.params.getYear;
            kswQueryParams.params.getMonth=$this.queryParams.params.getMonth;
            listData(kswQueryParams).then(response => {
              var title=$this.queryParams.params.getYear+"年"+$this.queryParams.params.getMonth+"月"+$this.getSenorName($this.queryParams.senorId)+"水位过程线";
              kswData = response.rows;
              $this.getChartData(btData,bjData,kswData,title);
            });
          });
        });
      }else{
        this.msgError("请选择月份和设备！")
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getData();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.params.month=null;
      this.resetForm("queryForm");
    },
    getSenorName(id){
      let actions = []
      Object.keys(this.senorOptions).some((key) => {
        if (this.senorOptions[key].id === id) {
          actions.push(this.senorOptions[key].name)
          return true
        }
      })
      return actions.join("")
    },
    //获取对应设备
    getSenorMatchSenorId(id){
      let actions = []
      Object.keys(this.senorOptions).some((key) => {
        if (this.senorOptions[key].id === id) {
          actions.push(this.senorOptions[key].matchSenorId)
          return true
        }
      })
      return actions.join("")
    },
    //拼接数据
    getChartData(btData,bjData,kswData,title){
      var $this=this;
      $this.chartData.title=title;
      $this.chartData.xAxisData=[];
      $this.chartData.seriesData=[{name: "", data: []},
        {name: "", data: []},
        {name: "", data: []}];
      var btMap={};
      var bjMap={};
      var kswMap={};
      for(var i in  btData){
        var getTime=parseTime(btData[i].getTime,"{y}-{m}-{d}");
        if($this.chartData.xAxisData.indexOf(getTime)<0){
          $this.chartData.xAxisData.push(getTime);
        }
        btMap.name=btData[i].senorName;
        btMap[getTime]=btData[i].data;
      }
      for(var i in  bjData){
        var getTime=parseTime(bjData[i].getTime,"{y}-{m}-{d}");
        if($this.chartData.xAxisData.indexOf(getTime)<0){
          $this.chartData.xAxisData.push(getTime);
        }
        bjMap.name=bjData[i].senorName;
        bjMap[getTime]=bjData[i].data;
      }
      for(var i in  kswData){
        var getTime=parseTime(kswData[i].getTime,"{y}-{m}-{d}");
        if($this.chartData.xAxisData.indexOf(getTime)<0){
          $this.chartData.xAxisData.push(getTime);
        }
        kswMap.name=kswData[i].senorName;
        kswMap[getTime]=kswData[i].data;
      }
      $this.chartData.seriesData[0].name=btMap.name;
      $this.chartData.seriesData[1].name=bjMap.name;
      $this.chartData.seriesData[2].name=kswMap.name;
      for(var i in $this.chartData.xAxisData){
        $this.chartData.seriesData[0].data.push(btMap[$this.chartData.xAxisData[i]]?btMap[$this.chartData.xAxisData[i]]:"");
        $this.chartData.seriesData[1].data.push(bjMap[$this.chartData.xAxisData[i]]?bjMap[$this.chartData.xAxisData[i]]:"");
        $this.chartData.seriesData[2].data.push(kswMap[$this.chartData.xAxisData[i]]?kswMap[$this.chartData.xAxisData[i]]:"");
      }
    }
  }
}
</script>

<style scoped>
</style>
