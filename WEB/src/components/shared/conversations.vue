<template>
    <div class="wrapper">
        <div class="sideBar">
            <div :class="{'preview':true,'selectedPreview': item.chosen}" v-for="item of conversations" :key="item.id"
                 @click="detail(item)">
                <div class="left">
                    +
                </div>
                <div class="context">
                    <div class="head">
                        <div class="sender">
                            {{item.username}}
                        </div>
                        <div class="time">
                            {{displayTime(item.date)}}
                        </div>
                    </div>
                    <div class="content">
                        {{item.recentMessage}}
                    </div>
                </div>
            </div>
        </div>
        <div class="detail">
            <div class="messages" id="messageList">
                <div :class="{box: true,me: isMine(message.senderID),other: !isMine(message.senderID)}"
                     v-for="message in messages.messageList">
                    <p class="messageTime">{{message.date}}</p>
                    <div class="messageContent">
                        <pre>{{message.content}}</pre>
                    </div>
                </div>
            </div>
            <div class="textArea">
                <p @keydown.enter="sendMessage" @keydown.shift.enter="enter" class="input"
                   contenteditable="true" placeholder="按Enter发送，Shift+Enter换行">
                </p>
            </div>
        </div>
    </div>
</template>

<script>

    export default {
        name: "conversation",
        data() {
            return {
                entered: false,
                messages: {
                    name: '',
                    id: '',
                    messageList: []
                },
                loaded: false,
                loading: true,
                messageVisible: false,
                username: '',
                id: '',
                searchInfo: '',
                selectedConversation: {
                    chosen: false
                },
                conversations: [{        //消息列表
                    id: '',       //对方id
                    username: '',         //对方用户名
                    date: '',     //最近一条消息的发送时间
                    recentMessage: '',    //最近一条消息
                    unreadCount: 0
                }]
            }
        },
        methods: {
            load() {
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/selectConversation/' + localStorage.username
                }).then(res => {
                    this.conversations = res.data.map(item => {
                        item.date = new Date(item.date);
                        item['chosen'] = false;
                        return item;
                    });
                    this.loading = false;
                })
            },
            detail(item) {
                this.id = item.id;
                this.username = item.username;
                this.messageVisible = true;
                this.selectedConversation.chosen = false;
                item.chosen = true;
                this.selectedConversation = item;
            },
            displayTime(time) {
                if (time.toLocaleDateString() === new Date().toLocaleDateString())
                    return time.toLocaleTimeString()
                return time.toLocaleDateString()
            },
            clear() {
                this.username = '';
                this.id = '';
                this.loaded = false;
            },
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
            loadMessages() {
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
        mounted() {
            this.load();
        }
    }
</script>

<style lang="scss" scoped>
    .wrapper {
        display: flex;
        height: 95%;
        background: rgba(255, 255, 255, 1.000);
        /*border-radius: 5px;*/
        /*padding: 20px 0;*/

        .sideBar {
            flex: 1;
            height: 100%;
            border-right: rgba(230, 230, 230, 1.000) 1px solid;
            overflow: scroll;

            .preview {
                text-align: left;
                position: relative;
                cursor: default;

                .left {
                    opacity: 0;
                    top: 10px;
                    position: absolute;
                    transform: rotate(45deg);
                    font-size: 23px;

                    &:hover {
                        color: #ff0000;
                    }
                }

                &:hover {

                    .left {
                        opacity: 1;
                    }
                }

                .context {
                    margin-left: 20px;
                    border-bottom: rgba(230, 230, 230, 1.000) 1px solid;
                    padding: 5px 5px 5px 5px;
                    font-size: 16px;

                    .head {
                        height: 25px;
                        position: relative;

                        .sender {
                            color: rgba(45, 45, 45, 1.000);
                            overflow: hidden;
                            font: {
                                weight: bold;
                            };
                            display: inline-block;
                        }

                        .time {
                            display: inline-block;
                            position: absolute;
                            color: rgba(128, 128, 128, 1.000);
                            right: 5px;
                        }
                    }

                    .content {
                        color: rgba(128, 128, 128, 1.000);
                        overflow: hidden;
                        height: 20px;
                    }
                }
            }

            .selectedPreview {
                background: rgb(0, 100, 224);

                .context {
                    border-bottom: rgba(0, 100, 224, 1.000) 1px solid;

                    .head {
                        .sender {
                            color: rgba(255, 255, 255, 1.000);
                        }

                        .time {
                            color: rgba(255, 255, 255, 1.000);
                        }
                    }

                    .content {
                        color: rgba(139, 177, 241, 1.000);
                    }
                }
            }
        }

        .detail {
            flex: 3;
            height: 100%;
            display: flex;
            flex-direction: column;

            .messageContent {
                padding: 5px 5px 5px 5px;
                display: block;
                overflow: auto;
                min-width: 1px;
                max-width: 200px;
                width: auto;
                background-color: rgba(231, 231, 231, 0.8);
            }

            .messageContent pre {
                text-align: left;
            }

            .messageTime {
                color: rgba(175, 175, 175, 0.83);
                font-size: 10px;
            }

            .other {
                float: left;
            }

            .other .messageTime {
                text-align: left;
            }

            .me {
                float: right;
            }

            .me .messageTime {
                text-align: right;
            }

            .messages {
                flex: 4;
                overflow: scroll;
            }

            .textArea {
                flex: 1;
                border-top: 1px solid #e1e8ec;
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
        }
    }
</style>