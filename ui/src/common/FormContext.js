import React, {createContext} from "react";
import {useForm} from "react-hook-form";

export const FormContext = createContext(null);

export default ({children}) => {

    const {register, handleSubmit, errors, setValue} = useForm({mode: 'all'});

    return <FormContext.Provider value={{register, handleSubmit, errors, setValue}}>{children}</FormContext.Provider>
}