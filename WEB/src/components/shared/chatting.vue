<template>
    <div>
        <el-main id="messageList">
            <div :class="{box: true,me: isMine(message.senderID),other: !isMine(message.senderID)}"
                 v-for="message in messages.messageList">
                <p class="time">{{message.date}}</p>
                <div class="content">
                    <pre>{{message.content}}</pre>
                </div>
            </div>
        </el-main>
        <el-footer>
            <p @keydown.enter="sendMessage" @keydown.shift.enter="enter" class="input"
               contenteditable="true" placeholder="按Enter发送，Shift+Enter换行">
            </p>
        </el-footer>
    </div>
</template>

<script>
    export default {
        name: "chatting",
        props: {
            id: String,
            username: String
        },
        data() {
            return {
                entered: false,
                messages: {
                    name: '',
                    id: '',
                    messageList: []
                },
            }
        },
        methods: {
            isMine(sender) {
                return sender === localStorage['username'];
            },
            sendMessage($event) {
                setTimeout(() => {
                    if (this.entered)
                        this.entered = false;
                    else {
                        $event.target.innerText = $event.target.innerText.slice(0, -2);
                        this.$axios({
                            method: 'GET',
                            url: 'http://localhost:8080/SRservice/sendMessage/' + localStorage.username + '/' + this.id,
                            params: {
                                content: $event.target.innerText
                            }
                        }).then(res => {
                            if (res.data === 'error')
                                this.$message.error("发送失败");
                        }).catch(e => {
                            this.$message.error("发送失败");
                            console.log(e);
                        })
                        $event.target.innerText = '';
                        $event.preventDefault();
                        this.$nextTick(() => {
                            document.getElementById('messageList').scrollTop = document.getElementById('messageList').scrollHeight;
                        })
                    }
                }, 60)
            },
            enter() {
                this.innerText += '\n';
                this.entered = true;
            },
            reload() {
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/receiveMessages/' + localStorage.username + '/' + this.id
                }).then(res => {
                    this.messages.messageList = res.data.reverse();
                    setTimeout(() => {
                        this.reload();
                    }, 500);
                }).catch(e => {
                    console.log(e);
                })
            },
            load() {
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/receiveMessages/' + localStorage.username + '/' + this.id
                }).then(res => {
                    this.messages.name = this.username;
                    this.messages.id = this.id;
                    this.messages.messageList = res.data.reverse();
                }).catch(e => {
                    console.log(e);
                })
            },
        },
        beforeMount() {
            this.load();
        },
        mounted() {
            this.reload();
        }
    }
</script>

<style scoped>
    .content {
        padding: 5px 5px 5px 5px;
        display: block;
        overflow: auto;
        min-width: 1px;
        max-width: 200px;
        width: auto;
        background-color: rgba(231, 231, 231, 0.8);
        border-radius: 5px;
    }

    .content pre {
        text-align: left;
    }

    .time {
        color: rgba(175, 175, 175, 0.83);
        font-size: 10px;
    }

    .other {
        float: left;
    }

    .other .content{
        background: rgba(243, 243, 243, 1.000);
    }

    .other .time {
        text-align: left;
    }

    .me {
        float: right;
    }

    .me .content{
        background: rgba(218, 245, 254, 1.000);
    }

    .me .time {
        text-align: right;
    }

    .el-main {
        border-top: 1px solid #e1e8ec;
        height: 300px;
        overflow: scroll;
    }

    .el-footer {
        border-top: 1px solid #e1e8ec;
        height: 100px;
        overflow: scroll;
    }

    .box {
        display: block;
        clear: both;
    }

    .input {
        margin-top: 10px;
        margin-right: 0;
        margin-left: 0;
        text-align: left;
        outline: none;
        width: 100%;
        min-height: 100%;
    }

    .input:empty:before {
        content: attr(placeholder);
        font-size: 13px;
        color: #999999;
    }

    .input:focus:before {
        content: none;
    }

    pre {
        white-space: pre-wrap;
        white-space: -moz-pre-wrap;
        white-space: -o-pre-wrap;
        *word-wrap: break-word;
        *white-space: normal;
    }

</style>
