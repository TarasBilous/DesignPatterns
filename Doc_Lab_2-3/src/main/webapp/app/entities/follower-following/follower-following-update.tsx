import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IInstagramUser } from 'app/shared/model/instagram-user.model';
import { getEntities as getInstagramUsers } from 'app/entities/instagram-user/instagram-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './follower-following.reducer';
import { IFollowerFollowing } from 'app/shared/model/follower-following.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFollowerFollowingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FollowerFollowingUpdate = (props: IFollowerFollowingUpdateProps) => {
  const [followingId, setFollowingId] = useState('0');
  const [followedById, setFollowedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { followerFollowingEntity, instagramUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/follower-following');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getInstagramUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...followerFollowingEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="instagramApp.followerFollowing.home.createOrEditLabel">Create or edit a FollowerFollowing</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : followerFollowingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="follower-following-id">ID</Label>
                  <AvInput id="follower-following-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="canFollowLabel">
                  <AvInput id="follower-following-canFollow" type="checkbox" className="form-check-input" name="canFollow" />
                  Can Follow
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="follower-following-following">Following</Label>
                <AvInput id="follower-following-following" type="select" className="form-control" name="following.id">
                  <option value="" key="0" />
                  {instagramUsers
                    ? instagramUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="follower-following-followedBy">Followed By</Label>
                <AvInput id="follower-following-followedBy" type="select" className="form-control" name="followedBy.id">
                  <option value="" key="0" />
                  {instagramUsers
                    ? instagramUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/follower-following" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  instagramUsers: storeState.instagramUser.entities,
  followerFollowingEntity: storeState.followerFollowing.entity,
  loading: storeState.followerFollowing.loading,
  updating: storeState.followerFollowing.updating,
  updateSuccess: storeState.followerFollowing.updateSuccess
});

const mapDispatchToProps = {
  getInstagramUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FollowerFollowingUpdate);
