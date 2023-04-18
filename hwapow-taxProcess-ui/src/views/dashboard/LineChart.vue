<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '500px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null,
      chartColor:["#1e0524","#9500ff","#ffc300","#4f6ef2","#11FF00","#ff1e00","#fd1e00","#FF005A"],
      chartSymbol:['circle','star','diamond','triangle','emptyCicle','emptyTriangle','pin']
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    /**
     * xAxisData X轴显示内容，数组，如['1月',‘2月’,'3月']
     * seriesData 有几种数据，显示几条线，包括数据。对象数组，如[{name:'水位',data:['10','20','30'],areaStyle:{}}，{name:'雨量',data:['13','24','35'],areaStyle:{}}]
     *            name:数据类型，
     *            data：具体数据，
     *            areaStyle：是否展示面积图
     * yAxisUnit y轴单位
     * yAxisDcale y刻度是否从0开始，默认true，不从0开始，false则是从0开始
     * yAxisDcaleMax y轴最大值
     * yAxisDcaleMin y轴最小值
     * title:标题
     * @param D
     */
    setOptions(D) {
      var legendData=[];
      var seriesData=[];
      for(var i in D.seriesData){
        legendData.push(D.seriesData[i].name)
        seriesData.push({
          name: D.seriesData[i].name,
          itemStyle: {
            normal: {
              color: this.chartColor[i%8],
              lineStyle: {
                color: this.chartColor[i%8],
                width: 2
              }
            }
          },
          smooth: true,
          symbol:this.chartSymbol[i%7],
          symbolSize:9,
          areaStyle:D.seriesData[i].areaStyle?D.seriesData[i].areaStyle:null,
          type: D.seriesData[i].type?D.seriesData[i].type:'line',
          data: D.seriesData[i].data,
          animationDuration: 2800,
          animationEasing: 'cubicInOut',
          label: {
            show: true
          }
        });
      }
      this.chart.setOption({
        title:{
          show:D.title?true:false,
          left:"center",
          text:D.title,
          textStyle:{
            color: '#000000',
            fontSize: 25
          }
        },
        xAxis: {
          data: D.xAxisData,
          boundaryGap: true,
          axisTick: {
            show: false
          }
        },
        toolbox: {
          show: true,
          feature: {
            saveAsImage: {},
            dataView: { readOnly: true },
            magicType: { type: [ 'bar'] },
            restore: {}
          }
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 80,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        yAxis: {
          axisTick: {
            show: false
          },
          scale:(D.yAxisDcale==false)?false:true,
          type:"value",
          axisLabel: {
            formatter:'{value}'+(D.yAxisUnit?D.yAxisUnit:"")
          },
          min: D.yAxisDcaleMin,
          max: D.yAxisDcaleMax
        },
        legend: {
          data: legendData,
          top:40
        },
        series: seriesData
      })
    }
  }
}
</script>
