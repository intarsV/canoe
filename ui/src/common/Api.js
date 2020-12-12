import axios from 'axios'
import {API_URL} from './Constants'

class Api {

    // config() {
    //     return {headers: {'Access-Control-Allow-Origin': 'http://localhost:8080'}}
    // };

    get(url) {
        return axios.get(API_URL + url);
    }

    getWithParams(url, searchParams) {
        return axios.get(API_URL + url, {params: searchParams});
    }

    post(url, data) {
        return axios.post(API_URL + url, data);
    }

    put(url, data) {
        return axios.put(API_URL + url, data);
    }
}

export default new Api();