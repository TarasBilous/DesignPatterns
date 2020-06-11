import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './instagram-user.reducer';
import { IInstagramUser } from 'app/shared/model/instagram-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInstagramUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InstagramUserUpdate = (props: IInstagramUserUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { instagramUserEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/instagram-user');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...instagramUserEntity,
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
          <h2 id="instagramApp.instagramUser.home.createOrEditLabel">Create or edit a InstagramUser</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : instagramUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="instagram-user-id">ID</Label>
                  <AvInput id="instagram-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="usernameLabel" for="instagram-user-username">
                  Username
                </Label>
                <AvField id="instagram-user-username" type="text" name="username" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="instagram-user-email">
                  Email
                </Label>
                <AvField id="instagram-user-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="passwordLabel" for="instagram-user-password">
                  Password
                </Label>
                <AvField id="instagram-user-password" type="text" name="password" />
              </AvGroup>
              <AvGroup>
                <Label id="ageLabel" for="instagram-user-age">
                  Age
                </Label>
                <AvField id="instagram-user-age" type="string" className="form-control" name="age" />
              </AvGroup>
              <AvGroup>
                <Label id="sexLabel" for="instagram-user-sex">
                  Sex
                </Label>
                <AvField id="instagram-user-sex" type="text" name="sex" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/instagram-user" replace color="info">
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
  instagramUserEntity: storeState.instagramUser.entity,
  loading: storeState.instagramUser.loading,
  updating: storeState.instagramUser.updating,
  updateSuccess: storeState.instagramUser.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InstagramUserUpdate);
