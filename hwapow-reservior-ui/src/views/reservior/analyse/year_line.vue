<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" >
        <el-form-item label="年份" prop="year">
          <el-date-picker
            v-model="queryParams.params.year"
            type="year"
            value-format="yyyy"
            placeholder="选择年份" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="设备" prop="senorId">
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
  name: "year_line",
  components: {LineChart},
  data() {
    return {
      senorOptions:null,
      queryParams: {
        params: {year: null},
        senorId: null,
      },
      chartData: {
        title:"",
        xAxisData: [],
        seriesData: [{name: "水位", data: []}],
        yAxisUnit: "米"
      }
    }
  },
  created() {
    this.initQuery();
  },
  methods:{
    initQuery(){
      listSenor().then(response => {
        this.senorOptions = response.rows;
      })
    },
    getData() {
      var $this=this;
      this.loading = true;
      if(this.queryParams.senorId&&this.queryParams.params.year){
        this.queryParams.params.getYear=this.queryParams.params.year;
        listData(this.queryParams).then(response => {
          $this.chartData.title=$this.queryParams.params.getYear+"年"+$this.getSenorName(this.queryParams.senorId)+"监测数据"
          $this.chartData.xAxisData=[];
          $this.chartData.seriesData[0].data=[];
          for(var i in  response.rows){
            $this.chartData.xAxisData.push(parseTime(response.rows[i].getTime,"{y}-{m}-{d}"));
            $this.chartData.seriesData[0].data.push(response.rows[i].data);
          }
        });
      }else{
        this.msgError("请选择年份和设备！")
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
      return actions.join('')
    }
  }
}
</script>

<style scoped>
</style>
