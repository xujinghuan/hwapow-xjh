<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" >
        <el-form-item label="日期" prop="day">
          <el-date-picker
            v-model="queryParams.params.day"
            value-format="yyyy-mm-dd"
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
import {listData} from "@/api/reservior/data";
import {listSection} from "@/api/reservior/section";

export default {
  name: "day_jinrun_line",
  components: {LineChart},
  data() {
    return {
      sectionOptions:null,
      queryParams: {
        params: {getYear: null},
        sectionId: null,
      },
      chartData: {
        xAxisData: ["T1-1", "T1-2", "T1-3", "T1-4"],
        seriesData: [{name: "1号断面", data: ["935", "930", "905", "900"],areaStyle:{}}],
        yAxisUnit: "米"
      }
    }
  },
  created() {
    this.initQuery();
  },
  methods:{
    initQuery(){
      listSection().then(response => {
        this.sectionOptions = response.rows;
      })
    },
    getData() {
      this.loading = true;
      listData(this.queryParams).then(response => {
        this.dataList = response.rows;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getData();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
    }
  }
}
</script>

<style scoped>
</style>
