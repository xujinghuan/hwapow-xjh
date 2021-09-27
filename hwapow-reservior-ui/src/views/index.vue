<template>
  <div class="app-container home">
    <el-row :gutter="20" class="el-row" type="flex" style="height: 100%">
      <el-col :span="24" style="height: 98%;">
        <el-card class="box-card" align="middle" style="height: 100%;">
          <div class="el-card-title mid">
            浍河水库<br>
          </div>
          <div class="el-card-content">
            <span style="color:#ffffff">水库</span>浍河水库水系属于山西省汾河支流，发源于山西省临汾市浮山县的桑王庙和沁水县的大岭村，两股水源流经翼城县大河口村时汇合，故称为浍河，流经翼城县、曲沃县、侯马市、新绛县，最后注入汾河。
            浍河水库是黄河流域，汾河一级支流浍河干流上的一座以防洪、灌溉为主，兼有养殖、旅游等综合利用的中型水库。坝址位于曲沃县史村镇西吉必村南3公里处。
            水库总库容达到9964万立方米，新增库容2447万立方米，可满足浍河灌区12万亩农田灌溉，保证利用率达到50%。
            防洪标准达到100年一遇洪水设计，1000年一遇洪水校核的部颁防洪安全标准。
            不仅能保护曲沃、侯马、新绛15万多人口、5万多亩农田、浍河二库、南同蒲铁路桥、新绛纺纱厂、大运公路及大运高速公路等的安全，而且能有效解决水库运行中防洪与灌溉的矛盾，
            浍河灌区是以浍河水库为主要水源的自流灌区，位于汾河、浍河汇合处三角黄土台地。受益范围包括曲沃县、侯马市的10个乡镇、92个自然村,设计灌溉面积16万亩，现有效灌溉面积12万亩。
            灌溉由干、支、斗、农四级渠系配套，总长达6226.9公里，其中干渠一条长32.6公里，设计流量为5.6m³/s；支渠十条，长44.36公里，干支斗农渠1336条，长549.3公里。
            浍河水库自拦洪受益至2003年，渠道引水131516万立方米，年均灌溉面积86000多亩、194000多亩。
            2012年初，禹门口提水东扩工程完工，黄河水被引入浍河水库。
          </div>
          <div class="el-card-image">
            <img src="../assets/hhsk/hhsk1.png"/>
            <img src="../assets/hhsk/hhsk2.jpg"/>
            <img src="../assets/hhsk/hhsk3.jpg"/>
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
  components: {
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

  .el-card-title {
    font-size: 30px;
    font-family: cursive;
    margin-bottom: 10px;
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

