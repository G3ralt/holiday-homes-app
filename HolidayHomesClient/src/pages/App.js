import React from "react";
import { Route, Switch } from "react-router-dom";
import Login from "./Login";
import Logout from "./Logout";
import Places from "./Places";
import AdminPage from "./AdminPage";
import TopMenu from "./TopMenu";
import Register from "./Register";
import Features from "./Documentation";
import WhoDidWhat from "./WhoDidWhat";
import DownloadApp from "./DownloadApp";
import FutureImplementations from "./FutureImplementations";
import Rentables from "./Rentables";
import CreatePlace from './CreatePlace';
import CreateRentable from './CreateRentable';
import UserDashboard from './UserDashboard';
import UserBookings from './UserBookings';
import UserPlaces from './UserPlaces';
import Welcome from './Welcome';

function App() {
    return (
        <div>
            <TopMenu />
            <Switch>
                <Route exact path="/" component={Welcome} />
                <Route path="/login" component={Login} />
                <Route path="/register" component={Register} />
                <Route path="/logout" component={Logout} />
                <Route exact path="/places" component={Places} />
                <Route path="/places/create" component={CreatePlace} />
                <Route path="/rentables/create" component={CreateRentable} />
                <Route path="/admin" component={AdminPage} />
                <Route path="/features" component={Features} />
                <Route path="/whodidwhat" component={WhoDidWhat} />
                <Route path="/downloadapp" component={DownloadApp} />
                <Route path="/futureimplementations" component={FutureImplementations} />
                <Route path="/rentables" component={Rentables} />
                <Route exact path="/myDashboard" component={UserDashboard} />
                <Route path="/myDashboard/myBookings" component={UserBookings} />
                <Route path="/myDashboard/myPlaces" component={UserPlaces} />
            </Switch>
        </div>
    );
}
export default App;