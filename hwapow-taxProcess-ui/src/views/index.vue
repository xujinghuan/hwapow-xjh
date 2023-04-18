<template>
  <div class="app-container home">
    <el-row  style="height: 422.5px;" :gutter="1" class="el-row" type="flex">
      <div class="yhscDiv">
        <span>用户手册</span>
        <ul>
          <li><a @click="handleDown(1)">业务股室操作手册--绩效监控</a></li>
          <li><a @click="handleDown(2)">二级预算单位操作手册--绩效监控</a></li>
          <li><a @click="handleDown(3)">一级部门操作手册--绩效监控</a></li>
          <li><a @click="handleDown(4)">绩效股室操作手册--绩效监控</a></li>
        </ul>
        <ul>
          <li><a @click="handleDown(5)">业务股室操作手册--绩效自评</a></li>
          <li><a @click="handleDown(6)">二级预算单位操作手册--绩效自评</a></li>
          <li><a @click="handleDown(7)">一级部门操作手册--绩效自评</a></li>
          <li><a @click="handleDown(8)">绩效股室操作手册--绩效自评</a></li>
        </ul>
      </div>
    </el-row>
    <el-row  style="height: 422.5px;" :gutter="1" class="el-row" type="flex">
    </el-row>
  </div>
</template>

<script>

import PieChart from "./dashboard/PieChart";
import BarChart from "./dashboard/BarChart";
import { downLoadFile } from '@/utils/zipdownload'


export default {
  name: "index",
  components: {BarChart, PieChart},
  data() {
    return {
      // 版本号
      version: "3.4.0",
      userName: "",
      content: {},
      url1:'/assets/yhsc/aa.doc'
    };
  },

  created() {

  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    linkRouter(href){
      this.$router.push(href);
    },
    handleDown(id){
      downLoadFile('/performance/homepage/download/' + id, null, error=>{
        this.msgError('文件下载失败！')
        console.log(error)
      })
    },
  },
};
</script>

<style scoped lang="scss">

.yhscDiv{
  span{
    font-size: 20px;//30
    font-family: cursive;
    margin-bottom: 5px;//10
    color: rgba(0, 0, 0, 0.45);//
    font-weight: 800;
  }
  li{
    margin: 10px 10px;
    a{

    }
    a:hover{
      color:#1c84c6;
    }
  }
}

.panel-group {
  margin-top: -140px;
  top: 50%;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 42px;
    }

    .card-panel-description {

      font-weight: bold;
      margin: 26px 0px 26px 5px;


      .card-panel-text {
        font-family: cursive;
        margin-left: 20px;
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 20px;
        font-weight: 800;
        margin-bottom: 12px;
        width: 100%;
      }

      .card-panel-num {
        float: right;
        font-size: 25px;
        margin-right: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}

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

  .el-card-title {
    font-size: 20px;//30
    font-family: cursive;
    margin-bottom: 5px;//10
    color: rgba(0, 0, 0, 0.45);//
    font-weight: 800;
  }

  .el-card-content {
    font-size: 23px;
    font-family: cursive;
    text-align: left;
  }

  .el-card-image img {
    width: 30%;
    margin: 1%;
  }
}
</style>

