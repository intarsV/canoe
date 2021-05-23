import Api from "./Api";

export const getList = (apiUrl, setList, setInfoMessage) => {
    Api.get(apiUrl)
        .then(response => {
            setList(response.data);
        })
        .catch((error) => {
            processErrorMessage(error, setInfoMessage);
        })
};

export const getListWithParams = (apiUrl, searchParams, setList, setInfoMessage) => {
    Api.getWithParams(apiUrl, searchParams)
        .then(response => {
            setList(response.data);
        })
        .catch((error) => {
            processErrorMessage(error, setInfoMessage);
        })
};

export const setFieldValues = (list, setValue) => {
    Object.entries(list).forEach(item => {
        setValue(item[0], item[1])
    })
};

export const processErrorMessage = (error, setInfoMessage) => {
    if (error.toString() === 'Error: Network Error') {
        setInfoMessage({type: 'error', msg: 'Server connection problems!'})
    } else {
        setInfoMessage({type: 'error', msg: error.response.data.message})
    }
};

export const processResponse = (response, list, setList, field) => {
    response.data[field] ?
        setList(list.filter(item => item.id !== response.data.id)) :
        setList(list.map(x => {
            if (x.id !== response.data.id) return x;
            return response.data;
        }));
};

export const msToTime = (s) => {
    // Pad to 2 or 3 digits, default is 2
    let pad = (n, z = 2) => ('00' + n).slice(-z);
    return pad(s / 3.6e6 | 0) + ':' + pad((s % 3.6e6) / 6e4 | 0) + ':' + pad((s % 6e4) / 1000 | 0) + '.' + pad(s % 1000, 3);
};

export const getBibValue = (reverse, bib) => {
    return reverse ? parseInt(bib) - 1 : parseInt(bib) + 1
};