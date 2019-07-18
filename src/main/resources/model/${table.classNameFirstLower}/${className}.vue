<style>
    #margin-top-20 {
        margin-top: 20px;
    }
    .queryParameterInput {
        width: 92%;
    }
</style>
<template>
    <div>
        <div style="width: 99%;margin: 0 auto;">
            <Collapse value="1">
                <Panel name="1">
                    查询条件
                    <p slot="content">

                        <Row id="margin-top-20">
                            <#list table.columns as column>
                            <Col span="4">
                                <Input placeholder="${column.columnNameLower}" v-model="${table.classNameFirstLower}QueryParameter.${column.columnNameLower}" clearable class="queryParameterInput">
                                    <span slot="prepend">${column.columnNameLower}:</span>
                                </Input>
                            </Col>
                            <#if column?counter%4 == 0 && column?counter!=0 >
                        </Row>
                        <Row id="margin-top-20">
                            </#if>
                            </#list>
                        </Row>
                    </p>

                </Panel>
            </Collapse>

            <Row style="margin-top: 20px;">

                <Button type="primary" style="width: 100px;" @click="query" ghost>查询</Button>

                <Button type="success" style="width: 100px;" @click="oepn${table.className}Modal('add')" ghost>新增</Button>

            </Row>

            <!--  table -->
            <Row style="margin-top: 20px;">
                <Table :columns="columns" :data="data" :loading="loding" height="600" border
                       ref="tableCsv"></Table>

                <!--<div style="margin: 10px;">
                    <div style="float: left;">
                        <Page :total="page.totol" :page-size="page.size" :current="page.current" @on-change="changePage"></Page>
                    </div>
                </div>-->
            </Row>

            <!-- Modal -->
            <Row>
                <Modal v-model="${table.classNameFirstLower}Modal" width="80%" :closable="false" title="" :mask-closable="false" footer-hide >


                    <Row id="margin-top-20">
                        <#list table.columns as column>
                        <Col span="4">
                        <Input placeholder="${column.columnNameLower}" v-model="${table.classNameFirstLower}Parameter.${column.columnNameLower}" clearable class="queryParameterInput">
                        <span slot="prepend">${column.columnNameLower}:</span>
                        </Input>
                        </Col>
                        <#if column?counter%4 == 0 && column?counter!=0 >
                    </Row>
                    <Row id="margin-top-20">
                    </#if>
                    </#list>
                    </Row>





                    <!-- Modal按钮-->
                    <Row id="margin-top-20" >
                        <Button type="info" style="width: 100px;" @click="save${table.className}" >保存</Button>

                        <Button type="default" style="width: 100px;" @click="close${table.className}Modal"  >取消</Button>
                    </Row>

                </Modal>

            </Row>


        </div>

    </div>
</template>

<script>

    export default {

        data() {
            return {
                ${table.classNameFirstLower}Modal:false,

                ${table.classNameFirstLower}Falg: true, // true = add  false = update

                ${table.classNameFirstLower}QueryParameter:{
                    pageIndex:1,
                    <#list table.columns as column>
                    ${column.columnNameLower}:'',
                    </#list>
                },
                ${table.classNameFirstLower}Parameter:{
                <#list table.columns as column>
                    ${column.columnNameLower}:'',
                </#list>
                },
                columns: [
                    {
                        title: '操作',
                        key: 'operating',
                        width: 140,
                        align: 'center',
                        render: (h, params) => {
                            const row = params.row;
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'info',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.oepn${table.className}Modal('update',row);
                                        }
                                    }
                                }, '编辑'),
                                h("Poptip", {
                                    props: {
                                        confirm: true,
                                        title: "您确定要作废吗？",
                                        transfer: true
                                    },
                                    on: {
                                        "on-ok": () => {
                                        }
                                    }
                                }, [
                                    h("Button", {
                                        style: {
                                            marginLeft: "15px"
                                        },
                                        props: {
                                            type: "error",
                                            placement: "top",
                                            size: "small"
                                        }
                                    }, "作废")
                                ])
                            ]);

                        }
                    },
                    <#list table.columns as column>
                    {
                        title: '${column.columnNameLower}',
                        key: '${column.columnNameLower}',
                        width: 120,
                        align: 'center'
                    },
                    </#list>
                ], // 表格列信息定义

                data:[],// 数据源

                loding:false,

                page: {
                    totol: 60,
                    size: 20,
                    current: 1
                },
            }
        },

        created() {
            this.query();
        },

        mounted() {

        },

        methods: {

            query(){
                let that = this;
                that.loding = true;

                that.$ajax
                    .post(this.$address_java + '${table.classNameFirstLower}/query', that.${table.classNameFirstLower}QueryParameter, that.$ajaxConfig).then(response => {
                    console.log(response);
                    that.data = response.data;
                    /*if (response.data.status == 100) {
                        let data = response.data.data;
                        that.data = data.list;
                        that.page.totol = data.total;
                        that.page.current = that.fieldManagementQueryParameter.pageIndex;
                    } else {
                        that.data = [];
                        that.page.totol = 0;
                    }*/
                    that.loding = false;
                });


            },

            /***
             * 作用 : 分页切换控制
             * 创建人: cr
             * 创建时间 ： 2019年3月20日15:45:15
             *
             * */
            changePage(value) {
                this.${table.classNameFirstLower}QueryParameter.pageIndex = value;
                this.query();
            },

            oepn${table.className}Modal(type,item){
                let that = this;
                if(type == 'add'){
                    that.${table.classNameFirstLower}Falg = true;
                }else{
                    that.${table.classNameFirstLower}Parameter = item;
                    that.${table.classNameFirstLower}Falg = false;
                }
                that.${table.classNameFirstLower}Modal = true;
            },

            close${table.className}Modal(){
                let that = this;
                that.${table.classNameFirstLower}Modal = false;
                that.query();
            },

            save${table.className}(){

                let that = this;

                if(that.${table.classNameFirstLower}Falg){

                    that.$ajax
                        .post(this.$address_java + '${table.classNameFirstLower}/add', that.${table.classNameFirstLower}Parameter, that.$ajaxConfig).then(response => {
                        console.log(response);
                        if(response.data == 100){
                            that.$Message.info("新增成功");
                            that.query();
                            that.close${table.className}Modal();
                        }
                    });

                }else{
                    that.$ajax
                        .post(this.$address_java + '${table.classNameFirstLower}/update', that.${table.classNameFirstLower}Parameter, that.$ajaxConfig).then(response => {
                        console.log(response);
                        if(response.data == 100){
                            that.$Message.info("修改成功");
                            that.query();
                            that.close${table.className}Modal();
                        }
                    });

                }


            },



        },
    }
</script>
