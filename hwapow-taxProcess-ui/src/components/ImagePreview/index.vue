<template>
  <transition name="viewer-fade">
    <div tabindex="-1" ref="el-image-viewer__wrapper" class="el-image-viewer__wrapper"
         :style="{ 'z-index': zIndex }"
    >
      <div class="el-image-viewer__mask" @click.self="handleMaskClick"></div>
      <!-- CLOSE -->
      <span class="el-image-viewer__btn el-image-viewer__close" @click="hide">
        <i class="el-icon-close"></i>
      </span>
      <!-- ARROW -->
      <template v-if="!isSingle">
        <span class="el-image-viewer__btn el-image-viewer__prev"
              :class="{ 'is-disabled': !infinite && isFirst }"
              @click="prev"
        >
          <i class="el-icon-arrow-left"/>
        </span>
        <span class="el-image-viewer__btn el-image-viewer__next"
              :class="{ 'is-disabled': !infinite && isLast }"
              @click="next"
        >
          <i class="el-icon-arrow-right"/>
        </span>
      </template>
      <!-- ACTIONS -->
      <div class="el-image-viewer__btn el-image-viewer__actions">
        <div class="el-image-viewer__actions__inner">
          <i class="el-icon-zoom-out" @click="handleActions('zoomOut')"></i>
          <i class="el-icon-zoom-in" @click="handleActions('zoomIn')"></i>
          <i class="el-image-viewer__actions__divider"/>
          <i :class="mode.icon" @click="toggleMode"/>
          <i class="el-image-viewer__actions__divider"/>
          <i class="el-icon-refresh-left" @click="handleActions('anticlocelise')"/>
          <i class="el-icon-refresh-right" @click="handleActions('clocelise')"/>
        </div>
      </div>
      <div v-if="imageObject.loading" v-loading="imageObject.loading" class="el-image-viewer__canvas">
        <div class="image-slot">
          <i class="el-icon-picture-outline"></i>
          <div>正在加载图片...</div>
        </div>
      </div>
      <div v-else-if="imageObject.isLoadError" class="el-image-viewer__canvas">
        <div class="image-slot-error">
          <i class="el-icon-picture-outline"></i>
          <div>图片加载失败</div>
        </div>
      </div>
      <!-- CANVAS -->
      <div v-else class="el-image-viewer__canvas">
        <img ref="img"
             class="el-image-viewer__img"
             :src="imageObject.imgSrc"
             :style="imgStyle"
             @load="handleImgLoad"
             @error="handleImgError"
             @mousedown="handleMouseDown"
             alt=""
        >
      </div>
    </div>
  </transition>
</template>

<script>
import { on, off } from 'element-ui/src/utils/dom'
import { rafThrottle, isFirefox } from 'element-ui/src/utils/util'
import { deepClone } from '@/utils'

const Mode = {
  CONTAIN: {
    name: 'contain',
    icon: 'el-icon-full-screen'
  },
  ORIGINAL: {
    name: 'original',
    icon: 'el-icon-c-scale-to-original'
  }
}

const mousewheelEventName = isFirefox() ? 'DOMMouseScroll' : 'mousewheel'

export default {
  name: 'ImagePreview',

  props: {
    urlList: {
      type: Array,
      default: () => []
    },
    zIndex: {
      type: Number,
      default: 2000
    },
    onSwitch: {
      type: Function,
      default: () => {
      }
    },
    onClose: {
      type: Function,
      default: () => {
      }
    },
    initialIndex: {
      type: Number,
      default: 0
    },
    appendToBody: {
      type: Boolean,
      default: true
    },
    maskClosable: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      index: this.initialIndex,
      isShow: false,
      infinite: true,
      loading: false,
      funcImgUrlList: { },
      mode: Mode.CONTAIN,
      imageObject: {
        currentIndex: null,
        loading: false,
        imgUrl: null,
        imgSrc: null,
        isLoadError: false
      },
      transform: {
        scale: 1,
        deg: 0,
        offsetX: 0,
        offsetY: 0,
        enableTransition: false
      }
    }
  },
  computed: {
    isSingle() {
      return this.urlList.length <= 1
    },
    isFirst() {
      return this.index === 0
    },
    isLast() {
      return this.index === this.urlList.length - 1
    },
    imgStyle() {
      const { scale, deg, offsetX, offsetY, enableTransition } = this.transform
      const style = {
        transform: `scale(${scale}) rotate(${deg}deg)`,
        transition: enableTransition ? 'transform .3s' : '',
        'margin-left': `${offsetX}px`,
        'margin-top': `${offsetY}px`
      }
      if (this.mode === Mode.CONTAIN) {
        style.maxWidth = style.maxHeight = '100%'
      }
      return style
    }
  },
  watch: {
    index: {
      handler: function(val) {
        this.reset()
        this.imageObject.currentIndex = val
        this.imageObject.loading = true
        this.changeCurrentImg()
      },
      immediate: true
    }
  },
  methods: {
    hide() {
      this.deviceSupportUninstall()
      this.onClose()
    },
    deviceSupportInstall() {
      this._keyDownHandler = rafThrottle(e => {
        const keyCode = e.keyCode
        switch (keyCode) {
          // ESC
          case 27:
            this.hide()
            break
          // SPACE
          case 32:
            this.toggleMode()
            break
          // LEFT_ARROW
          case 37:
            this.prev()
            break
          // UP_ARROW
          case 38:
            this.handleActions('zoomIn')
            break
          // RIGHT_ARROW
          case 39:
            this.next()
            break
          // DOWN_ARROW
          case 40:
            this.handleActions('zoomOut')
            break
        }
      })
      this._mouseWheelHandler = rafThrottle(e => {
        const delta = e.wheelDelta ? e.wheelDelta : -e.detail
        if (delta > 0) {
          this.handleActions('zoomIn', {
            zoomRate: 0.015,
            enableTransition: false
          })
        } else {
          this.handleActions('zoomOut', {
            zoomRate: 0.015,
            enableTransition: false
          })
        }
      })
      on(document, 'keydown', this._keyDownHandler)
      on(document, mousewheelEventName, this._mouseWheelHandler)
    },
    deviceSupportUninstall() {
      off(document, 'keydown', this._keyDownHandler)
      off(document, mousewheelEventName, this._mouseWheelHandler)
      this._keyDownHandler = null
      this._mouseWheelHandler = null
    },
    handleImgLoad() {
      this.loading = false
    },
    handleImgError(e) {
      this.loading = false
      e.target.alt = '加载失败'
    },
    handleMouseDown(e) {
      if (this.loading || e.button !== 0) return

      const { offsetX, offsetY } = this.transform
      const startX = e.pageX
      const startY = e.pageY
      this._dragHandler = rafThrottle(ev => {
        this.transform.offsetX = offsetX + ev.pageX - startX
        this.transform.offsetY = offsetY + ev.pageY - startY
      })
      on(document, 'mousemove', this._dragHandler)
      on(document, 'mouseup', () => {
        off(document, 'mousemove', this._dragHandler)
      })

      e.preventDefault()
    },
    handleMaskClick() {
      if (this.maskClosable) {
        this.hide()
      }
    },
    reset() {
      this.transform = {
        scale: 1,
        deg: 0,
        offsetX: 0,
        offsetY: 0,
        enableTransition: false
      }
    },
    toggleMode() {
      if (this.loading) return

      const modeNames = Object.keys(Mode)
      const modeValues = Object.values(Mode)
      const index = modeValues.indexOf(this.mode)
      const nextIndex = (index + 1) % modeNames.length
      this.mode = Mode[modeNames[nextIndex]]
      this.reset()
    },
    changeCurrentImg() {
      this.imageObject.imgUrl = this.urlList[this.index]
      const imgSrc = this.urlList[this.index]
      const srcType = typeof imgSrc
      if (srcType === 'string') {
        this.imageObject.imgSrc = imgSrc
        this.isLoadError = false
        this.imageObject.loading = false
        return
      }

      if (srcType !== 'object') {
        this.isLoadError = true
        this.loading = false
        return
      }

      const loadMethod = imgSrc.method
      if (typeof loadMethod !== 'function') {
        this.isLoadError = true
        this.imageObject.loading = false
        return
      }

      if(!this.funcImgUrlList[this.index.toString()])
        this.funcImgUrlList[this.index.toString()] = deepClone(imgSrc)

      loadMethod(this.imageObject, imgSrc.params)
    },
    prev() {
      if (this.isFirst && !this.infinite) return
      const len = this.urlList.length
      this.index = (this.index - 1 + len) % len
    },
    next() {
      if (this.isLast && !this.infinite) return
      const len = this.urlList.length
      this.index = (this.index + 1) % len
    },
    handleActions(action, options = {}) {
      if (this.loading) return
      const { zoomRate, rotateDeg, enableTransition } = {
        zoomRate: 0.2,
        rotateDeg: 90,
        enableTransition: true,
        ...options
      }
      const { transform } = this
      switch (action) {
        case 'zoomOut':
          if (transform.scale > 0.2) {
            transform.scale = parseFloat((transform.scale - zoomRate).toFixed(3))
          }
          break
        case 'zoomIn':
          transform.scale = parseFloat((transform.scale + zoomRate).toFixed(3))
          break
        case 'clocelise':
          transform.deg += rotateDeg
          break
        case 'anticlocelise':
          transform.deg -= rotateDeg
          break
        case 'refresh':
          this.changeCurrentImg()
          break
      }
      transform.enableTransition = enableTransition
    }
  },
  mounted() {
    this.deviceSupportInstall()
    if (this.appendToBody) {
      document.body.appendChild(this.$el)
    }
    // add tabindex then wrapper can be focusable via Javascript
    // focus wrapper so arrow key can't cause inner scroll behavior underneath
    this.$refs['el-image-viewer__wrapper'].focus()
  },
  destroyed() {
    // if appendToBody is true, remove DOM node after destroy
    if (this.appendToBody && this.$el && this.$el.parentNode) {
      this.$el.parentNode.removeChild(this.$el)
    }
  }
}
</script>
<style lang="scss" scoped>
.image-slot {
  width: 200px;
  height: 200px;
  background-color: #616161;
  opacity: 0.6;
  text-align: center;
  color: #deb887;
  .el-icon-picture-outline {
    font-size: 60px;
    margin-top: 50px;
  }
}

.image-slot-error {
  width: 200px;
  height: 200px;
  background-color: #1c1c1c;
  opacity: 0.6;
  text-align: center;
  color: #a0a0a0;
  .el-icon-picture-outline {
    font-size: 60px;
    margin-top: 50px;
  }
}

.el-loading-mask {
  .el-loading-spinner {
    top: 47%;
    margin-top: 40px;

    .circular {
      width: 80px;
      height: 80px;
    }
  }
}
</style>
