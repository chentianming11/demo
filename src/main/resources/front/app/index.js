/**
 * Created by mac on 2018/7/28.
 */
// main.js
import React, {Fragment, Component, createRef} from 'react';
import {BrowserRouter, Route, Link, NavLink} from 'react-router-dom';
import {render} from 'react-dom';
import 'antd/dist/antd.css';
import styles from  './style.less';
import {Layout, Menu, Breadcrumb, Icon, Form} from 'antd';


import Login from  './component/login/Login';
import RegistrationForm from  './component/register/RegistrationForm';

const {Header, Content, Footer} = Layout;
const {SubMenu, MenuItemGroup, Item} = Menu;

class App extends Component {

    constructor(props) {
        super(props);
    }


    render() {


        return (

            <Layout>
                <Header className={styles.header}>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['首页']}
                        className={styles.menu}
                    >

                        <Item key="logo">
                            <NavLink
                                to="/"
                                className={styles.nav}
                            >logo</NavLink>
                        </Item>
                        <Item key="首页">
                            <NavLink
                                to="/"
                                className={styles.nav}
                            >首页</NavLink>
                        </Item>
                        <Item key="登录">﻿
                            <NavLink
                                to="/login"
                                className={styles.nav}
                            >登录</NavLink>

                        </Item>
                        <Item key="注册">
                            <NavLink
                                to="/register"
                                className={styles.nav}
                            >注册</NavLink>
                        </Item>
                        <Item key="写文章">
                            <NavLink
                                to="/write"
                                className={styles.nav}
                            >写文章</NavLink>
                        </Item>
                    </Menu>
                </Header>
                <Content style={{padding: '50px 200px', marginTop: 64}}>

                    <Route exact={true} path='/login' component={Login}/>
                    <Route exact={true} path='/register' component={RegistrationForm}/>

                </Content>
                <Footer style={{textAlign: 'center'}}>
                    Ant Design ©2018 Created by Ant UED
                </Footer>
            </Layout>

        )
    }
}


render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
    , document.getElementById('root'));