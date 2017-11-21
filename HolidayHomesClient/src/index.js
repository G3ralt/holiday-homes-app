import React from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import { HashRouter,BrowserRouter} from "react-router-dom"
import App from "./pages/App"


ReactDOM.render((
  <HashRouter>
    <App/>
  </HashRouter>
), document.getElementById('root'))