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

import { IFollowerFollowing, defaultValue } from 'app/shared/model/follower-following.model';

export const ACTION_TYPES = {
  FETCH_FOLLOWERFOLLOWING_LIST: 'followerFollowing/FETCH_FOLLOWERFOLLOWING_LIST',
  FETCH_FOLLOWERFOLLOWING: 'followerFollowing/FETCH_FOLLOWERFOLLOWING',
  CREATE_FOLLOWERFOLLOWING: 'followerFollowing/CREATE_FOLLOWERFOLLOWING',
  UPDATE_FOLLOWERFOLLOWING: 'followerFollowing/UPDATE_FOLLOWERFOLLOWING',
  DELETE_FOLLOWERFOLLOWING: 'followerFollowing/DELETE_FOLLOWERFOLLOWING',
  RESET: 'followerFollowing/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFollowerFollowing>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FollowerFollowingState = Readonly<typeof initialState>;

// Reducer

export default (state: FollowerFollowingState = initialState, action): FollowerFollowingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FOLLOWERFOLLOWING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FOLLOWERFOLLOWING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FOLLOWERFOLLOWING):
    case REQUEST(ACTION_TYPES.UPDATE_FOLLOWERFOLLOWING):
    case REQUEST(ACTION_TYPES.DELETE_FOLLOWERFOLLOWING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FOLLOWERFOLLOWING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FOLLOWERFOLLOWING):
    case FAILURE(ACTION_TYPES.CREATE_FOLLOWERFOLLOWING):
    case FAILURE(ACTION_TYPES.UPDATE_FOLLOWERFOLLOWING):
    case FAILURE(ACTION_TYPES.DELETE_FOLLOWERFOLLOWING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FOLLOWERFOLLOWING_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_FOLLOWERFOLLOWING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FOLLOWERFOLLOWING):
    case SUCCESS(ACTION_TYPES.UPDATE_FOLLOWERFOLLOWING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FOLLOWERFOLLOWING):
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

const apiUrl = 'api/follower-followings';

// Actions

export const getEntities: ICrudGetAllAction<IFollowerFollowing> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FOLLOWERFOLLOWING_LIST,
    payload: axios.get<IFollowerFollowing>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFollowerFollowing> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FOLLOWERFOLLOWING,
    payload: axios.get<IFollowerFollowing>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFollowerFollowing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FOLLOWERFOLLOWING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IFollowerFollowing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FOLLOWERFOLLOWING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFollowerFollowing> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FOLLOWERFOLLOWING,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
