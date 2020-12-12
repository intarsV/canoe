import React from "react";
import McuData from "./McuData";
import RaceProcessing from "./RaceProcessing";
import Results from "./Results";
import {Col, Row} from "reactstrap";


const RaceManager = () => {

    return (
        <div>
            <Row>
                <Col lg={7}>
                    <McuData/>
                </Col>
                <Col lg={5}>
                    <Results/>
                </Col>
            </Row>
            <br/>
            <RaceProcessing/>
        </div>
    )
}

export default RaceManager