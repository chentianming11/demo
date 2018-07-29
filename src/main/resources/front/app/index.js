/**
 * Created by mac on 2018/7/28.
 */
// main.js
import React from 'react';
import {render} from 'react-dom';
import 'antd/dist/antd.css';
import './index.css';
import Nav from './component/nav/Nav';

render(
    <Nav/>
    , document.getElementById('root'));