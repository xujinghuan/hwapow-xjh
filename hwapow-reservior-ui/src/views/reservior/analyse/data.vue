<template>
  <el-container class="fullContainer">
    <el-header style="height: auto;">
      <el-form class="searchForm" size="mini" :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="年份" prop="getYear">
          <el-date-picker
            v-model="queryParams.getYear"
            type="year"
            value-format="yyyy"
            placeholder="选择年份" clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="月份" prop="getMonth">
          <el-select v-model="queryParams.getMonth" placeholder="请选择月份" clearable>
            <el-option
              v-for="item in monthOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期" prop="getDay">
          <el-select v-model="queryParams.getDay" placeholder="请选择日期" clearable>
            <el-option
              v-for="item in dayOptions"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="断面" prop="sectionId">
          <el-select v-model="queryParams.sectionId" placeholder="请选择所属断面" clearable>
            <el-option
              v-for="dict in sectionOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备" prop="senorId">
          <el-select v-model="queryParams.senorId" placeholder="请选择所属设备" clearable>
            <el-option
              v-for="dict in senorOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <iframe style="width: 100%;height: 99%" :src="'http://'+backIPPort+'/ureport/preview?_t=6' +
       '&getYear='+queryParams.getYear+'&getMonth='+queryParams.getMonth+'&getDay='+queryParams.getDay+
       '&senorId='+queryParams.senorId+'&sectionId='+queryParams.sectionId+'&orgId='+selOrgId+
       '&_u=file:data-report.ureport.xml'">
      </iframe>
    </el-main>
  </el-container>
</template>

<script>
import settings from '/public/settings.js'
import {listSection} from "@/api/reservior/section";
import {listSenor} from "@/api/reservior/senor";
import {getUserProfile} from "@/api/system/user";

export default {
  name: "Data",
  components: {},
  data() {
    return {
      selOrgId: null,
      // 查询参数
      queryParams: {
        getYear: null,
        getMonth: null,
        getDay: null,
        sectionId: null,
        senorId: null
      },
      sectionOptions: [],//断面集合
      senorOptions: [],//设备集合
      monthOptions: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
      dayOptions: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"]
    };
  },
  created() {
    this.initQuery();
    this.getUserInfo();
  },
  computed: {
    backIPPort: function () {
      return settings.backIPPort;
    }
  },
  methods: {
    initQuery() {
      listSection().then(response => {
        this.sectionOptions = response.rows;
      });
      listSenor().then(response => {
        this.senorOptions = response.rows;
      });

    },
    getUserInfo() {
      var $this = this;
      getUserProfile().then(response => {
        $this.selOrgId = response.data.orgId;
      });
    }
  }
};
</script>
