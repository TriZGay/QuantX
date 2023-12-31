import * as bootstrap from 'bootstrap'
import { defineComponent, ref, watch } from 'vue'
const { Alert } = bootstrap

export default defineComponent({
    name: "Message",
    props: {
        show: {
            type: Boolean,
            default: false
        },
        msg: {
            type: String,
            default: ""
        },
        duration: {
            type: Number,
            default: 1000
        }
    },
    emits: ['update:show'],
    setup(props, ctx) {
        const alertRef = ref()
        const isShow = ref(props.show)

        watch(() => props.show, (newVal, oldVal) => {
            isShow.value = newVal
            if (newVal) {
                let timer
                clearInterval(timer)
                timer = setTimeout(() => {
                    isShow.value = false;
                    Alert.getOrCreateInstance(alertRef).close();
                    // alertRef.value.close()
                    ctx.emit("update:show", false)
                }, props.duration)
            }
        })

        const appendAlert = (message: string, type: string) => {
            const wrapper = document.createElement("div")
            wrapper.innerHTML = [
                `<div class="alert alert-${type} alert-dismissible fade show" role="alert">`,
                `   <div>${message}</div>`,
                '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
                '</div>'
            ].join('')

            alertRef.value.append(wrapper)
        }

        const onMessageOpen = () => {
            appendAlert('Nice, you triggered this alert message!', 'success')
        }

        return () => (
            <div>
                <button type="button" class="btn btn-primary" onClick={onMessageOpen}>打开</button>
                <div ref={alertRef}></div>
            </div>
        )
    },
})
