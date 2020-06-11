import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInstagramUser, defaultValue } from 'app/shared/model/instagram-user.model';

export const ACTION_TYPES = {
  FETCH_INSTAGRAMUSER_LIST: 'instagramUser/FETCH_INSTAGRAMUSER_LIST',
  FETCH_INSTAGRAMUSER: 'instagramUser/FETCH_INSTAGRAMUSER',
  CREATE_INSTAGRAMUSER: 'instagramUser/CREATE_INSTAGRAMUSER',
  UPDATE_INSTAGRAMUSER: 'instagramUser/UPDATE_INSTAGRAMUSER',
  DELETE_INSTAGRAMUSER: 'instagramUser/DELETE_INSTAGRAMUSER',
  RESET: 'instagramUser/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInstagramUser>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type InstagramUserState = Readonly<typeof initialState>;

// Reducer

export default (state: InstagramUserState = initialState, action): InstagramUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INSTAGRAMUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INSTAGRAMUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_INSTAGRAMUSER):
    case REQUEST(ACTION_TYPES.UPDATE_INSTAGRAMUSER):
    case REQUEST(ACTION_TYPES.DELETE_INSTAGRAMUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_INSTAGRAMUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INSTAGRAMUSER):
    case FAILURE(ACTION_TYPES.CREATE_INSTAGRAMUSER):
    case FAILURE(ACTION_TYPES.UPDATE_INSTAGRAMUSER):
    case FAILURE(ACTION_TYPES.DELETE_INSTAGRAMUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSTAGRAMUSER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_INSTAGRAMUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_INSTAGRAMUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_INSTAGRAMUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_INSTAGRAMUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/instagram-users';

// Actions

export const getEntities: ICrudGetAllAction<IInstagramUser> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_INSTAGRAMUSER_LIST,
    payload: axios.get<IInstagramUser>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IInstagramUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INSTAGRAMUSER,
    payload: axios.get<IInstagramUser>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IInstagramUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INSTAGRAMUSER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IInstagramUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INSTAGRAMUSER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInstagramUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INSTAGRAMUSER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
