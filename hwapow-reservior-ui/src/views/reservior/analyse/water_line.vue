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
        <el-form-item label="断面" prop="sectionId">
          <el-select v-model="queryParams.sectionId" clearable>
            <el-option
              v-for="dict in sectionOptions"
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
import {listData} from "@/api/reservior/data";
import {parseTime} from "@/utils/hwapow";
import {listSection} from "@/api/reservior/section";

export default {
  name: "water_line",
  components: {LineChart},
  data() {
    return {
      sectionOptions: null,
      queryParams: {
        params: {month: null,getYear:null,getMonth:null},
        sectionId: null,
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
      var query={params:{codeNotIn:['9','11','12','1','10','2','5','8']}}
      listSection(query).then(response => {
        this.sectionOptions = response.rows;
      })
    },
    getData() {
      var $this=this;
      var btData=null;//坝体数据
      var kswData=null;//库位水数据
      this.loading = true;
      this.queryParams.params.order="get_time asc";
      if(this.queryParams.sectionId&&this.queryParams.params.month) {
        this.queryParams.params.getYear = this.queryParams.params.month.substr(0, 4);
        this.queryParams.params.getMonth = this.queryParams.params.month.substr(5, 2);
        //获取坝体数据
        listData(this.queryParams).then(response => {
          btData = response.rows;
          //获取库水位数据
          var kswQueryParams={};
          kswQueryParams.params={getYear:null,getMonth:null};
          kswQueryParams.params.senorType="2";
          kswQueryParams.params.getYear=$this.queryParams.params.getYear;
          kswQueryParams.params.getMonth=$this.queryParams.params.getMonth;
          listData(kswQueryParams).then(response => {
            var title=$this.queryParams.params.getYear+"年"+$this.queryParams.params.getMonth+"月"+$this.getSectionName($this.queryParams.sectionId)+"水位过程线";
            kswData = response.rows;
            $this.getChartData(btData,kswData,title);
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
    getSectionName(id){
      let actions = []
      Object.keys(this.sectionOptions).some((key) => {
        if (this.sectionOptions[key].id === id) {
          actions.push(this.sectionOptions[key].name)
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
    getChartData(btData,kswData,title){
      var $this=this;
      $this.chartData.title=title;
      $this.chartData.xAxisData=[];
      $this.chartData.seriesData=[{name: "", data: []}];
      var btMap=[];
      var kswMap={};
      for(var i in  btData){
        var getTime=parseTime(btData[i].getTime,"{y}-{m}-{d}");
        if($this.chartData.xAxisData.indexOf(getTime)<0){
          $this.chartData.xAxisData.push(getTime);
        }
        if(!btMap[btData[i].senorName]){
          btMap[btData[i].senorName]={name:null};
        }
        btMap[btData[i].senorName].name=btData[i].senorName;
        btMap[btData[i].senorName][getTime]=btData[i].data;
      }
      for(var i in  kswData){
        var getTime=parseTime(kswData[i].getTime,"{y}-{m}-{d}");
        if($this.chartData.xAxisData.indexOf(getTime)<0){
          $this.chartData.xAxisData.push(getTime);
        }
        kswMap.name=kswData[i].senorName?kswData[i].senorName:"库水位";
        kswMap[getTime]=kswData[i].data;
      }
      $this.chartData.seriesData[0].name=kswMap.name;
      var t=0;
      for(var i in btMap){
        t++;
        $this.chartData.seriesData[t]={name: "", data: []};
        $this.chartData.seriesData[t].name=btMap[i].name;
      }
      for(var i in $this.chartData.xAxisData){
        $this.chartData.seriesData[0].data.push(kswMap[$this.chartData.xAxisData[i]]?kswMap[$this.chartData.xAxisData[i]]:"");
        t=0;
        for(var j in btMap){
          t++;
          $this.chartData.seriesData[t].data.push(btMap[j][$this.chartData.xAxisData[i]]?btMap[j][$this.chartData.xAxisData[i]]:"");
        }
      }
    }
  }
}
</script>

<style scoped>
</style>
