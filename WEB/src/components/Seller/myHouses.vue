<template>
  <p>
    {{context}}
  </p>
</template>

<script>

  export default {
    name: "myHouses",
    data() {
      return {
        houses: [],
        context:""
      }
    },
    mounted() {
      let data;
      //SOAP 1.1 请求报文格式，1.2在网上可以找到
      data = '<?xml version="1.0" encoding="utf-8"?>';
      data = data + '<soap:Envelope  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:xsd="http://www.w3.org/2001/XMLSchema"  xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">';
      data = data + '<soap:Body>';
      data = data + '<helloWorld xmlns="http://hit.com/">';//这里就是发布的方法名和xml文档中的命名空间地址
      data = data + '</helloWorld>';
      data = data + '</soap:Body>';
      data = data + '</soap:Envelope>';

      // this.$axios({
      //   url:"api/Apache_CXF_war_exploded/service/readerService2?wsdl",
      //   method: "GET",
      //   param: data,
      //   headers: {
      //     "Content-Type": "text/xml; charset=utf-8"
      //   }
      // }).then(response=>{
      //   this.context=response.data;
      // }).catch(e=>{
      //   console.log(e);
      // })
      let xmlhttp = new XMLHttpRequest();
      let URL = "api/service/readerService2?wsdl";
      xmlhttp.open("POST", URL, false);
      xmlhttp.setRequestHeader("Content-Type", "text/xml; charset=utf-8"); //SOAP 1.1为text/xml ； 1.2为 application/soap+xml
      xmlhttp.send(data);


      this.context=xmlhttp.responseText;
    }
  }
</script>

<style scoped>

</style>
