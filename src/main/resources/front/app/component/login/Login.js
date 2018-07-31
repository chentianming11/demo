import React, {Fragment, Component, createRef} from 'react';
import {BrowserRouter, Route, Link, NavLink, Redirect} from 'react-router-dom';

import {Form, Icon, Input, Button, Checkbox} from 'antd';

import styles from './style.less';
const FormItem = Form.Item;

class Login extends Component {

    constructor(props) {
        super(props);
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
            }
        });
    }


    render() {

        const {getFieldDecorator} = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit} className={styles.login}>
                <FormItem>
                    {getFieldDecorator('userName', {
                        rules: [{required: true, message: 'Please input your username!'}],
                    })(
                        <Input prefix={<Icon type="user" style={{color: 'rgba(0,0,0,.25)'}}/>} placeholder="Username"/>
                    )}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('password', {
                        rules: [{required: true, message: 'Please input your Password!'}],
                    })(
                        <Input prefix={<Icon type="lock" style={{color: 'rgba(0,0,0,.25)'}}/>} type="password"
                               placeholder="Password"/>
                    )}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('remember', {
                        valuePropName: 'checked',
                        initialValue: true,
                    })(
                        <Checkbox>Remember me</Checkbox>
                    )}
                    <a className={styles.forgot} href="">Forgot password</a>
                    <Button type="primary" htmlType="submit" className={styles.button}>
                        Log in
                    </Button>
                    <Link to="/register">register now!</Link>
                </FormItem>
            </Form>
        );
    }

}

const WrappedNormalLoginForm = Form.create()(Login);

export default WrappedNormalLoginForm;


