<template>
  <el-container class="fullContainer">
    <el-main>
      <div class="collect-left">
        <div class="collect-background">
          <img src="../../../assets/hhsk/background.png" width="100%"/>
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
        <el-main>
          <el-table :data="dataList">
            <el-table-column
              label=""
              type="index"
              width="40"
              align="center">
              <template scope="scope">
                <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
              </template>
            </el-table-column>
            <!--<el-table-column label="水库" align="center" prop="orgName" width="100"/>-->
            <el-table-column label="设备" align="center" prop="senorName" width="64"/>
            <!--<el-table-column label="断面" align="center" prop="sectionName"/>-->
            <el-table-column label="淹没高度" align="center" prop="rawData" width="74">
              <template slot-scope="scope">
                <span>{{transformUnitToM(scope.row.rawData,scope.row.backDataUnit)}}m</span>
              </template>
            </el-table-column>
            <el-table-column label="水位/流量" align="center" prop="data" width="94">
              <template slot-scope="scope">
                <span :style="{color:((scope.row.warnMax&&scope.row.warnMin&&(scope.row.data>=scope.row.warnMax||scope.row.data<=scope.row.warnMin))?'red':'black')}">{{ (scope.row.senorType!="3"?(scope.row.data+"m"):(scope.row.data+"L/s"))+(scope.row.capacity?("/"+scope.row.capacity+"m³"):"")}}</span>
              </template>
            </el-table-column>
            <el-table-column label="采集时间" align="center" prop="getTime" width="150">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.getTime,'{y}-{m}-{d} {h}:{i}:{s}') }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
        <el-footer>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getDataList"
          />
        </el-footer>
      </div>
      <div v-if="time>0" class="mask">
        <div style="opacity: 1;z-index: 101;color:#00ff89;">{{ msg }}</div>
        <div style="opacity: 1;z-index: 101;color:#00ff89;font-size: 80px">{{ time }}</div>
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
import {parseTime} from "@/utils/hwapow";


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
      msg:"",
      time:0,
      minorData:0,//需要监测测的数量
      backNum:0,//已经返回数据的数量
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
    //巡测
    this.initPage();
  },
  beforeDestroy() {
    closeSocket();
  },
  methods: {
    initPage(){
      this.queryParams.params={
        getYear: parseTime(new Date(),'{y}'),
        getMonth: parseTime(new Date(),'{m}'),
        getDay: parseTime(new Date(),'{d}')
      }
      listData(this.queryParams).then(response => {
        this.total = response.total;
        if(this.total<=0){
          this.sectionClick(null)
        }
      });
    },
    //查询监测数据
    getDataList() {
      this.queryParams.params={
        getYear: parseTime(new Date(),'{y}'),
        getMonth: parseTime(new Date(),'{m}'),
        getDay: parseTime(new Date(),'{d}')
      }
      this.queryParams.params.order="senor_sort asc";
      listData(this.queryParams).then(response => {
        this.total = response.total;
        this.dataList = response.rows;
        var msg=null;
        for(var i in this.dataList){
          if(this.dataList[i].warnMax&&this.dataList[i].warnMin&&(this.dataList[i].data>=this.dataList[i].warnMax||this.dataList[i].data<=this.dataList[i].warnMin)){
              msg=this.dataList[i].senorName+"报警！";
          }
        }
        if(msg){
          this.$message({ showClose: true, message: msg, type: "error" ,duration: 300000000});
        }
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
      listSenor().then(response => {
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
        sendToALL().then(response => {
          this.addCountdown(response.msg,response.data);
        });
      } else {
        sendToSection(item.id).then(response => {
          this.addCountdown(response.msg,response.data);
        });
      }
    },
    senorClick(item) {
      sendToSenor(item.id).then(response => {
        this.addCountdown(response.msg,response.data);
      });
    },
    //添加计时器
    addCountdown(msg,data){
      this.minorData=data;
      this.backNum=0;
      this.time=data*10+10;//10代表每台设备回传时间10秒钟
      this.msg=msg;
      this.timer = setInterval(()=>{
        this.time--;
        if(this.time<0){
          clearInterval(this.timer)
          this.msgSuccess("数据回传完毕！");
        }
      },1000)
    },
    //添加后端监听
    addListener() {
      var $this = this;
      openSocket(function receiveMsg(msg) {
        if (msg.code == "sernorData") {//需要判断code是什么。也就是取到的数据是什么，才能判断做什么事情
          $this.queryParams.pageNum = 1;
          $this.queryParams.pageSize = 30;
          $this.getDataList();
          $this.backNum++;
          if($this.backNum>=$this.minorData){
            $this.time=0;
            this.msgSuccess("数据回传完毕！");
          }
        }
      });
    }
  }
};
</script>
<style scoped lang="scss">
.collect-left {
  width: calc(100% - 425px);
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
  color: #1A228F;
  background: url("../../../assets/images/single.gif") no-repeat;
  height: 38px;
  line-height: 25px;
  font-weight: 800;
  background-size: 17px;
  background-position: top;
  padding-top: 13px;
}

.collect-data {
  width: 425px;
  height: 100%;
  float: right;
  border-left: 1px solid #dedede;
}

.collect-data .el-main{
  height: calc(100% - 60px);
}



.collect-section {
  width: 100%;
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

.mask {
  background-color: rgb(0, 0, 0);
  opacity: 0.3;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 100;
  font-size: 20px;
  font-weight: 600;
  text-align: center;
}

</style>
