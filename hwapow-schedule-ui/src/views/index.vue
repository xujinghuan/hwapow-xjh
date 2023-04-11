<template>
  <div class="app-container home">
    <el-row :gutter="20"  class="el-row" type="flex" style="height: 50%">
      <el-col :span="12" style="height: 98%;">
        <el-card class="box-card hwapow-card"  align="middle" style="height: 100%;">
          <div slot="header" class="clearfix">
            <span class="hwapow-card-header">今日待办</span>
          </div>
          <div class="text item">
            <el-row class="hwapow-card-row" v-for="(item,i) in todayToDoList" :key="i">
              <el-col :span="2"><span>{{i+1}}</span></el-col><el-col :span="11" align="left"><span>{{ item.toDoThing }}</span></el-col>
              <el-col :span="3"><span>{{ parseTime(item.toDoTime, '{y}-{m}-{d}') }}</span></el-col>
              <el-col :span="8">
                <!--未完成并且未关闭的可以有完成按钮-->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleDone(item)"
                >完成</el-button>
                <!--未完成并且未关闭的可以有完成按钮-->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-sort"
                  @click="handleTodoTime(item)"
                >顺延</el-button>
                <!--未完成并且未关闭的可以有关闭按钮-->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleClose(item)"
                >关闭</el-button>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" style="height: 98%;">
        <el-card class="box-card hwapow-card"  align="middle" style="height: 100%;">
          <div slot="header" class="clearfix">
            <span class="hwapow-card-header">逾期未办</span>
          </div>
          <div class="text item">
            AAA
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20"  class="el-row" type="flex" style="height: 50%" >
      <el-col :span="12" style="height: 98%;" >
        <el-card class="box-card hwapow-card" align="middle" style="height: 100%;">
          <div slot="header" class="clearfix">
            <span class="hwapow-card-header">近14日待办事项</span>
          </div>
          <div class="text item">
            AAA
          </div>
        </el-card>
      </el-col>
      <el-col :span="6" style="height: 98%;" >
        <el-card class="box-card hwapow-card" align="middle" style="height: 100%;">
          <div slot="header" class="clearfix">
            <span class="hwapow-card-header">近14天生日提醒</span>
          </div>
          <div class="text item">
            AAA
          </div>
        </el-card>
      </el-col>
      <el-col :span="6" style="height: 98%;">
        <el-card class="box-card hwapow-card"  align="middle" style="height: 100%;">
          <div slot="header" class="clearfix">
            <span class="hwapow-card-header">近1月身份证过期提醒</span>
          </div>
          <div class="text item">
            AAA
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import {listToDoThing} from "@/api/schedule/toDoThing";
import {parseTime} from "@/utils/hwapow"

export default {
  name: "index",
  components:{
  },
  data() {
    return {
      // 今日待办事情
      todayToDoList: []
    };
  },
  created() {
    this.getTodayToDoList();
  },
  methods: {
    //获取今日待办事情
    getTodayToDoList(){
      var queryParam={
        isClose:0,
        isDone:0,
        toDoTime:parseTime(new Date(), '{y}-{m}-{d}')
      }
      listToDoThing(queryParam).then(response=>{
        this.todayToDoList=response.rows;
      });
    }
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

