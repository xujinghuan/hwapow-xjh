<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="日期" prop="getDay">
          <el-date-picker
            v-model="queryParams.getDay"
            value-format="yyyy-MM-dd"
            placeholder="选择日期" clearable>
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
import {getLastData, listData} from "@/api/reservior/data";
import {listSection} from "@/api/reservior/section";
import {parseTime} from "@/utils/hwapow";

export default {
  name: "day_jinrun_line",
  components: {LineChart},
  data() {
    return {
      sectionOptions: null,
      queryParams: {
        params:{},
        getDay:null,
        sectionId: null,
      },
      chartData: {
        xAxisData: [],
        seriesData: [{name: "水位", data: []}],
        yAxisUnit: "米"
      }
    }
  },
  created() {
    this.initQuery();
  },
  methods: {
    initQuery() {
      var query={params:{codeNotIn:['9','11','12']}}
      listSection(query).then(response => {
        this.sectionOptions = response.rows;
      })
    },
    getData() {
      var $this=this;
      this.loading = true;
      this.queryParams.params.order="senor_sort asc";
      if (this.queryParams.sectionId && this.queryParams.getDay) {
        this.queryParams.params.getYear=this.queryParams.getDay.substr(0,4);
        this.queryParams.params.getMonth=this.queryParams.getDay.substr(5,2);
        this.queryParams.params.getDay=this.queryParams.getDay.substr(8,2);
        listData(this.queryParams).then(response => {
          $this.chartData.title=$this.queryParams.params.getYear+"年"+$this.queryParams.params.getMonth+"月"+$this.queryParams.params.getDay+"日"+$this.getSectionName(this.queryParams.sectionId)+"断面监测数据"
          $this.chartData.xAxisData=[];
          $this.chartData.seriesData[0].data=[];
          for(var i in  response.rows){
            $this.chartData.xAxisData.push(response.rows[i].senorName);
            $this.chartData.seriesData[0].data.push(response.rows[i].data);
          }
        });
      }else{
        this.msgError("请选择日期和断面！")
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getData();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.params.day = null;
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
      return actions.join('')
    }
  }
}
</script>

<style scoped>
</style>
