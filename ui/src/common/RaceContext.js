import React, {createContext, useState} from "react";

export const RaceContext = createContext(null);

export default ({children}) => {

    const [raceEventData, setRaceEventData] = useState({event: 0, subEvent: 0, teamMode: false, eventFormat: 0});
    const [selection, setSelection] = useState({event:'', subEvent:''})
    const [raceData, setRaceData] = useState([]);

    return <RaceContext.Provider value={{raceEventData, setRaceEventData, raceData, setRaceData, selection, setSelection}}>{children}</RaceContext.Provider>
}