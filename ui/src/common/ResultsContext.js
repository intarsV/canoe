import React, {createContext, useState} from "react";

export const ResultsContext = createContext(null);

export default ({children}) => {

    const [resultData, setResultData] = useState({})

    return <ResultsContext.Provider value={{resultData, setResultData}}>{children}</ResultsContext.Provider>
}