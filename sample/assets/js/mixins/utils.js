import VueNotifications from 'vue-notifications'

export default {
    notifications: {
        showSuccess: {
            title: 'Success!',
            message: 'Action successful',
            type: VueNotifications.types.success
        },
        showError: {
            title: 'Oops!',
            message: "Ocurrió un error",
            type: VueNotifications.types.error
        }
    },
    methods: {
        hello() {
            console.log('hello')
        }
    }
}