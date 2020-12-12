import React from 'react';
import Country from "./pages/Country";
import {Route, Router, Switch} from 'react-router-dom'
import history from './common/History';
import BoatClass from "./pages/BoatClass";
import Competitor from "./pages/Competitor";
import Group from "./pages/Group";
import Event from "./pages/Event";
import McuData from "./pages/McuData";
import EventRegistry from "./pages/EventRegistry";
import RaceProcessing from "./pages/RaceProcessing";
import NavMenu from "./menu/NavMenu";
import './App.css';
import RaceManager from "./pages/RaceManager";
import Results from "./pages/Results";
import RaceConfig from "./pages/RaceConfig";

const App = () => {
    return (
        <div className="container">
            <Router history={history}>
                <div className="row">
                    <div className="col-lg-2 col-md-2">
                        <NavMenu/>
                    </div>
                    <div className="page-header col-lg-10 col-md-10">
                        <h2>Initex timing</h2>
                        <Switch>
                            <Route path="/boat-class" exact render={() => <BoatClass/>}/>
                            <Route path="/competitor" exact render={() => <Competitor/>}/>
                            <Route path="/country" exact render={() => <Country/>}/>
                            <Route path="/group" exact render={() => <Group/>}/>
                            <Route path="/event" exact render={() => <Event/>}/>
                            <Route path="/raceManager" exact render={()=> <RaceManager/>}/>
                            <Route path="/mcu-data" exact render={() => <McuData/>}/>
                            <Route path="/event-registry" exact render={() => <EventRegistry/>}/>
                            <Route path="/race-processing" exact render={() => <RaceProcessing/>}/>
                            <Route path="/results" exact render={() => <Results/>}/>
                            <Route path="/race-config" exact render={() => <RaceConfig/>}/>
                        </Switch>
                    </div>
                </div>
            </Router>
        </div>


        /*<Router history={history}>*/

        /*    <div class="container">*/


        /*        <NavMenu />*/

        /*<Switch>*/

        /*    <Route path="/boat-class" exact render={() => <BoatClass/>}/>*/
        /*    <Route path="/competitor" exact render={() => <Competitor/>}/>*/
        /*    <Route path="/country" exact render={() => <Country/>}/>*/

        /*    <Route path="/group" exact render={() => <Group/>}/>*/

        /*    <Route path="/event" exact render={() => <Event/>}/>*/

        /*    <Route path="/mcu-data" exact render={() => <McuData/>}/>*/

        /*    <Route path="/event-registry" exact render={() => <EventRegistry/>}/>*/
        /*    <Route path="/race-processing" exact render={() => <RaceProcessing/>}/>*/

        /*    <Route path="/emitter" exact render={() => <EmitterTest/>}/>*/

        /*</Switch>*/

        //
        //     </div>
        // </Router>
    )
        ;
};

export default App;
