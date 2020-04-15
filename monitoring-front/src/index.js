import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {Route, Link, BrowserRouter} from 'react-router-dom'
import ViewDetails from "./components/ViewDetails";

const Routing = () => {
    return (
        <BrowserRouter>
            <Route exact path="/" component={App}/>
            <Route path="/viewDetails/:id" component={ViewDetails}/>
        </BrowserRouter>
    )
};
export default Routing;

ReactDOM.render(<Routing/>, document.getElementById('root'));

serviceWorker.unregister();
