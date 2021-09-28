<template>
  <el-container class="fullContainer">
    <el-main>
      <div class="collect-left">
        <div class="collect-background">
          <img src="../../../assets/hhsk/background.jpg" width="100%"/>
          <a class="senor-position" v-for="item in senorList" :style="{left:item.x+'%',top:item.y+'%'}" href="#"
             @click="senorClick(item)">{{ item.name }}</a>
        </div>
        <div class="collect-section">
          <a class="collect-section-item" @click="sectionClick(null)">巡测</a>
          <a class="collect-section-item" v-for="item in sectionList" href="#"
             @click="sectionClick(item)">{{ item.name }}</a>
        </div>
      </div>
      <div class="collect-data">
        <el-table :data="dataList">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center">
            <template scope="scope">
              <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="水库" align="center" prop="orgName"/>
          <el-table-column label="传感器设备" align="center" prop="senorName"/>
          <el-table-column label="断面" align="center" prop="sectionName"/>
          <el-table-column label="淹没高度" align="center" prop="backInstruction"/>
          <el-table-column label="实时水位" align="center" prop="rawData"/>
          <el-table-column label="库容" align="center" prop="data"/>
          <el-table-column label="采集时间" align="center" prop="getTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.getTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getDataList"
        />
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {listSection} from "@/api/reservior/section";
import {listSenor} from "@/api/reservior/senor";
import {listData} from "@/api/reservior/data";
import {getUserProfile} from "@/api/system/user";
import {sendToALL, sendToSection, sendToSenor} from "@/api/reservior/sendToPort";
import {closeSocket, openSocket} from "@/utils/hwapowUtils";

export default {
  name: "collect",
  components: {},
  data() {
    return {
      //断面列表
      sectionList: [],
      //设备列表
      senorList: [],
      loginUser: null,
      // 总条数
      total: 0,
      //采集数据
      dataList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30
      }
    };
  },
  created() {
    this.getSectionList();
    this.getLoginUser();
    this.getSenorList();
    this.getDataList();
    this.addListener();
  },
  beforeDestroy() {
    closeSocket();
  },
  methods: {
    //查询监测数据
    getDataList() {
      listData(this.queryParams).then(response => {
        this.total = response.total;
        this.dataList = response.rows;
      });
    },
    /** 查询水库断面列表 */
    getSectionList() {
      listSection().then(response => {
        this.sectionList = response.rows;
      });
    },
    /** 查询设备列表 */
    getSenorList() {
      listSenor(this.queryParams).then(response => {
        this.senorList = response.rows;
      });
    },
    getLoginUser() {
      getUserProfile().then(response => {
        this.loginUser = response.data;
      });
    },
    sectionClick(item) {
      if (item == null) {
        console.log(111);
        sendToALL().then(response => {
          this.msgSuccess(response.msg);
        });
      } else {
        sendToSection(item.id).then(response => {
          this.msgSuccess(response.msg);
        });
      }
    },
    senorClick(item){
      sendToSenor(item.id).then(response => {
        this.msgSuccess(response.msg);
      });
    },
    //添加后端监听
    addListener(){
      var $this=this;
      openSocket(function receiveMsg(msg){
        if(msg.code=="sernorData"){//需要判断code是什么。也就是取到的数据是什么，才能判断做什么事情
          $this.queryParams.pageNum=1;
          $this.queryParams.pageSize=30;
          $this.getDataList();
        }
      });
    }
  }
};
</script>
<style scoped lang="scss">
.collect-left {
  width: 50%;
  height: 100%;
  float: left;
}

.collect-background {
  width: 100%;
  position: relative;
}

.senor-position {
  position: absolute;
  z-index: 100;
  color: #ffffff;
  background: url("../../../assets/images/single.gif") no-repeat;
  padding-left: 25px;
  height: 25px;
  line-height: 25px;
}

.collect-data {
  width: 50%;
  height: 100%;
  float: right;
}

.collect-section {
  width: 50%;
  padding: 10px;
  float: left;
}

.collect-section-item {
  background-color: #56a4f6;
  color: #ffffff;
  padding: 5px 10px;
  float: left;
  margin: 5px;
  border-radius: 3px;
}
</style>
