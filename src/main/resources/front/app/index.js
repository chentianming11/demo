/**
 * Created by mac on 2018/7/28.
 */
// main.js
import React from 'react';
import {render} from 'react-dom';
import 'antd/dist/antd.css';
import {Layout, Menu, Breadcrumb} from 'antd';
import './index.css';

const {Header, Content, Footer} = Layout;
const {SubMenu, MenuItemGroup} = Menu;
render(
    <Layout>
        <Header style={{position: 'fixed', zIndex: 1, width: '100%'}}>
            <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={['2']}
                style={{lineHeight: '64px'}}
            >

                <Menu.Item key="发现">发现</Menu.Item>
                <Menu.Item key="关注">关注</Menu.Item>

                <SubMenu title={<span>消息</span>}>
                    <MenuItemGroup title="A组">
                        <Menu.Item key="评论">评论</Menu.Item>
                        <Menu.Item key="简信">简信</Menu.Item>
                    </MenuItemGroup>
                    <MenuItemGroup title="B组">
                        <Menu.Item key="喜欢">喜欢</Menu.Item>
                        <Menu.Item key="关注">关注</Menu.Item>
                    </MenuItemGroup>
                </SubMenu>
            </Menu>
        </Header>
        <Content style={{padding: '0 50px', marginTop: 64}}>

            <div style={{background: '#fff', padding: 24, minHeight: 380}}>Content</div>
        </Content>
        <Footer style={{textAlign: 'center'}}>
            Ant Design ©2016 Created by Ant UED
        </Footer>
    </Layout>
    , document.getElementById('root'));