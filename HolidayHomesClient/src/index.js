import React from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import './styles/rating.css';
import './styles/pinMap.css';
import './styles/priceTag.css';
import './styles/availability.css';
import { HashRouter } from 'react-router-dom';
import App from './pages/App';


ReactDOM.render((
  <HashRouter>
    <App/>
  </HashRouter>
), document.getElementById('root'))