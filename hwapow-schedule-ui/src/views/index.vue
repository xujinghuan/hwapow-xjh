<template>
  <div class="app-container home">
    <el-row :gutter="20"  class="el-row" type="flex" style="height: 50%">
      <el-col :span="12" style="height: 98%;">
        <el-card class="box-card"  align="middle" style="height: 100%;">
          <div class="el-card__body mid">
            通知公告<br>
            欢迎使用！
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" style="height: 98%;">
        <el-card class="box-card"  align="middle" style="height: 100%;">
          <div class="el-card__body mid">
            <BarChart />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20"  class="el-row" type="flex" style="height: 50%" >
      <el-col :span="12" style="height: 98%;" >
        <el-card class="box-card" align="middle" style="height: 100%;">
          <div class="el-card__body mid">
            <raddar-chart />
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" style="height: 98%;">
        <el-card class="box-card"  align="middle" style="height: 100%;">
          <div class="el-card__body mid">
            <pie-chart />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

export default {
  name: "index",
  components:{
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      // 版本号
      version: "3.4.0"
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

