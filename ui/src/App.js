import React from 'react';
import {Route, Router, Switch} from 'react-router-dom'
import history from './common/History';
import Competitor from "./pages/Competitor";
import Event from "./pages/Event";
import McuData from "./pages/McuData";
import EventRegistry from "./pages/EventRegistry";
import RaceProcessing from "./pages/RaceProcessing";
import NavMenu from "./menu/NavMenu";
import './App.css';
import Results from "./pages/Results";
import RaceConfig from "./pages/RaceConfig";
import Config from "./pages/Config";

const App = () => {
    return (
        <div className="container">
            <Router history={history}>
                <div className="row">
                    <NavMenu/>
                    <Switch>
                        <Route path="/competitor" exact render={() => <Competitor/>}/>
                        <Route path="/event" exact render={() => <Event/>}/>
                        <Route path="/mcu-data" exact render={() => <McuData/>}/>
                        <Route path="/event-registry" exact render={() => <EventRegistry/>}/>
                        <Route path="/race-processing" exact render={() => <RaceProcessing/>}/>
                        <Route path="/results" exact render={() => <Results/>}/>
                        <Route path="/race-config" exact render={() => <RaceConfig/>}/>
                        <Route path="/config" exact render={() => <Config/>}/>
                    </Switch>
                </div>
            </Router>
        </div>
    )
};

export default App;
