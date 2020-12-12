import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App';
import FormContextProvider from "./common/FormContext";
import InfoMsgContextContextProvider from "./common/InfoMsgContext";
import RaceContextProvider from "./common/RaceContext";
import ResultsContext from "./common/ResultsContext";

ReactDOM.render(
    <InfoMsgContextContextProvider>
        <FormContextProvider>
            <RaceContextProvider>
                <ResultsContext>
                    <App/>
                </ResultsContext>
            </RaceContextProvider>
        </FormContextProvider>
    </InfoMsgContextContextProvider>,
    document.getElementById('root')
);

