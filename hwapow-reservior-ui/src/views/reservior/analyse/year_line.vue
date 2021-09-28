<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true" >
        <el-form-item label="年份" prop="getYear">
          <el-date-picker
            v-model="queryParams.params.getYear"
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

export default {
  name: "year_line",
  components: {LineChart},
  data() {
    return {
      senorOptions:null,
      queryParams: {
        params: {getYear: null},
        senorId: null,
      },
      chartData: {
        xAxisData: ["2021-01", "2021-02", "2021-03", "2021-04", "2021-05", "2021-06"],
        seriesData: [{name: "水位", data: ["935", "930", "915", "910", "905", "900"]}],
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
