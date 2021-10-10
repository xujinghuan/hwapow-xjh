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

export default {
  name: "water_line",
  components: {LineChart},
  data() {
    return {
      senorOptions: null,
      queryParams: {
        params: {month: null},
        senorId: null,
      },
      chartData: {
        xAxisData: ["2021-01-01", "2021-01-02", "2021-01-03", "2021-01-04", "2021-01-05", "2021-01-06"],
        seriesData: [{name: "T1-1", data: ["935", "932", "932", "931", "931", "933"]},
          {name: "J1-1", data: ["900", "900", "900", "900", "900", "900"]},
          {name: "库水位", data: ["1000", "1000", "1000", "1000", "1000", "1000"]}],
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
      this.queryParams.params.month=null;
      this.resetForm("queryForm");
    }
  }
}
</script>

<style scoped>
</style>
