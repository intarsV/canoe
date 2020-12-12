import React, {createContext, useState} from "react";

export const InfoMsgContext = createContext(null);

export default ({children}) => {

    const [infoMessage, setInfoMessage] = useState({type: '', msg: ''});

    return <InfoMsgContext.Provider value={{infoMessage, setInfoMessage}}>{children}</InfoMsgContext.Provider>
}