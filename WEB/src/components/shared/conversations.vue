<template>
    <div class="wrapper" v-loading="loading">
        <div class="sideBar">
            <div :class="{'preview':true,'selectedPreview': item.chosen}" :key="item.id" @click="detail(item)"
                 v-for="item of conversations">
                <div class="left">
                    +
                </div>
                <div class="context">
                    <div class="head">
                        <div class="sender">
                            {{item.username}}
                        </div>
                        <div class="time">
                            {{item.date}}
                        </div>
                    </div>
                    <div class="content">
                        {{item.recentMessage}}
                    </div>
                </div>
            </div>
        </div>
        <div class="detail">
            <div class="messages" id="messageList"
                 v-loading="messageList.length === 0">
                <div :class="{box: true,me: isMine(message.senderID),other: !isMine(message.senderID)}"
                     v-for="message in messageList">
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
                messageList: [],
                loaded: false,
                loading: true,
                messageListLoading: false,
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
                        item.date = item.date.toLocaleDateString() === new Date().toLocaleDateString() ? item.date.toLocaleTimeString() : item.date.toLocaleDateString();
                        item['chosen'] = false;
                        return item;
                    });
                    this.detail(this.conversations[0]);
                    this.loading = false;
                })
            },
            detail(item) {
                setTimeout(() => {
                    this.id = item.id;
                    this.username = item.username;
                    this.selectedConversation.chosen = false;
                    item.chosen = true;
                    this.messageList = [];
                    this.selectedConversation = item;
                    this.messageListLoading = true;
                }, 0);
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
            loadMessages() {
                if (!this.selectedConversation.id)
                    setTimeout(() => {
                        this.loadMessages();
                    }, 1000);
                this.$axios({
                    method: 'GET',
                    url: 'http://localhost:8080/SRservice/receiveMessages/' + localStorage.username + '/' + this.selectedConversation.id
                }).then(res => {
                    this.messageList = res.data.reverse();
                    setTimeout(() => {
                        this.loadMessages();
                    }, 4000);
                }).catch(e => {
                    console.log(e);
                })
            },
        },
        beforeMount() {
            this.load();
            this.loadMessages();
        }
    }
</script>

<style lang="scss" scoped>
    .wrapper {
        display: flex;
        height: 100%;
        background: rgba(255, 255, 255, 1.000);
        border-radius: 10px;
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
                border-bottom: rgba(230, 230, 230, 1.000) 1px solid;

                .left {
                    opacity: 0;
                    top: 10px;
                    position: absolute;
                    transform: rotate(45deg);
                    font-size: 23px;

                    &:hover {
                        color: rgba(0, 181, 246, 1.000);
                    }
                }

                /*&:hover {*/

                /*    .left {*/
                /*        opacity: 1;*/
                /*    }*/
                /*}*/

                .context {
                    margin-left: 20px;
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
                background: rgba(227, 228, 228, 1.000);
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
                border-radius: 5px;
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

            .other .messageContent {
                background: rgba(243, 243, 243, 1.000);
            }


            .me {
                float: right;
            }

            .me .messageTime {
                text-align: right;
            }

            .me .messageContent {
                background: rgba(218, 245, 254, 1.000);
            }


            .messages {
                flex: 4;
                overflow: scroll;
                padding: 10px;
            }

            .textArea {
                flex: 1;
                border-top: 1px solid #e1e8ec;
                overflow: scroll;
                white-space: pre;
                padding: 15px;
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