/**
 * Created by mac on 2018/7/29.
 */
import React, {Fragment, Component, createRef} from 'react';
import {Router, Route, Link, NavLink} from 'react-router-dom';
import {Layout, Menu, Icon, Breadcrumb} from 'antd';

const {Header, Content, Footer} = Layout;
const {SubMenu, MenuItemGroup, Item} = Menu;
export default class Nav extends Component {
    constructor(props) {
        super(props);
    }

    render() {


        return (
            <Layout>
                <Header style={{position: 'fixed', zIndex: 1, width: '100%'}}>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['2']}
                        style={{lineHeight: '64px'}}
                    >

                        <Item key="发现">logo</Item>
                        <Item key="关注">首页</Item>

                    </Menu>
                </Header>
                <Content style={{padding: '0 50px', marginTop: 64}}>

                    <div style={{background: '#fff', padding: 24, minHeight: 380}}>Content</div>
                </Content>
                <Footer style={{textAlign: 'center'}}>
                    Ant Design ©2016 Created by Ant UED
                </Footer>
            </Layout>
        )
    }
}