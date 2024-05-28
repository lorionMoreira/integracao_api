import React, {  useState,useMemo, useEffect  } from "react";
import {
  Button,
  Container,
  Card,
  Alert,
  Collapse,
  CardBody,
  CardTitle,
  CardSubtitle,
  UncontrolledAlert,  
  Form,
  Input,
  FormFeedback, 
  Label 
} from "reactstrap";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logoutUser } from "../../store/actions";
//Import Breadcrumb
import { del, get, post, put,setAuthToken } from "../../helpers/api_helper";
import Breadcrumbs from "../../components/Common/Breadcrumb";
import TableContainer from '../../components/Common/TableContainerNoFilter';
import Pagination from '../../components/Common/Pagination';
//i18n
import { withTranslation } from "react-i18next";

// Formik validation
import * as Yup from "yup";
import { useFormik } from "formik";

const WeatherBuscar = props => {
  const { demoData } = useSelector(state => ({
    demoData: state.Login.demoData,
  }));

//console.log('componentes');
//console.log(demoData)
  const history = useNavigate();
  const dispatch = useDispatch();
  



const [loading, setLoading] = useState(false);
const [cities, setCities] = useState([]);

const fetchInitial = async (page) => {
  try {
    setLoading(true);
    const response = await get('/api/weather/v1/get');
    setLoading(false);
    console.log("response")
    console.log(response)


  } catch (error) {
    //setError(error);  

    console.log("_inicial")
    setLoading(false);
    console.log(error)

    if (error?.message == "Request failed with status code 403") {
      dispatch(logoutUser(history));
    }

    if (error?.message == "Network Error") {
      dispatch(logoutUser(history));
    }

  }
};

  useEffect(() => {
    fetchOptions();
  }, []);

  useEffect(() => {
    const obj = JSON.parse(localStorage.getItem("authUser"));
    setAuthToken(obj?.token);

    fetchInitial();
  }, []);

  const fetchOptions = async () => {
    console.log('craziii')
    try {
      const response = await get('/api/weather/v1/cities');
      console.log('unidade')
      console.log(response)
      setCities(response);
    } catch (error) {
      
    }
  };



  useEffect(() => {
    if (loading) {
      document.body.style.cursor = 'wait';
    } else {
      document.body.style.cursor = 'default';
    }
  
    // Clean up function to reset the cursor when the component unmounts
    return () => {
      document.body.style.cursor = 'default';
    };
  }, [loading]);

  return (
    <React.Fragment>

        <div className="page-content">
          <Container fluid>
            {/* Render Breadcrumb */}
            <Breadcrumbs
              title={props.t("BUSCAR")}
              breadcrumbItem={props.t("API DE PRPEVISÂO DO TEMPO")}
            />
            <Card className="p-3">
              <CardBody>
                    <CardTitle>Selecione a cidade para verificar a previsão do tempo atual</CardTitle>
                    <CardSubtitle className="font-14 text-muted">
                      Use o campo abaixo para adicionar uma disciplina ao sistema.
                    </CardSubtitle>

                    <Form
                      className="form-horizontal"
                      onSubmit={(e) => {
                        e.preventDefault();
                        validation.handleSubmit();
                        return false;
                      }}
                    >
                    <div className="mb-3">
                      <Label className="form-label">cidade</Label>
                      <Input
                        name="cidade"
                        className="form-control"
                        placeholder="Digite o cidade da disciplina"
                        type="select"
                        onChange={validation.handleChange}
                        onBlur={validation.handleBlur}
                        value={validation.values.cidade || ""}
                        invalid={validation.touched.unidade && validation.errors.unidade}
                      />
                      <option value="">Selecione</option>
                        {options2.map((option) => (
                        <option key={option.id} value={option.id}>
                          {option.nome}
                        </option>
                        ))}
                      {validation.touched.cidade && validation.errors.cidade ? (
                        <FormFeedback type="invalid">{validation.errors.cidade}</FormFeedback>
                      ) : null}
                    </div>


                      <div className="d-flex justify-content-end">
                        <Button color="primary" type="submit">
                          Submeter formulário
                        </Button>
                      </div>

                    </Form>

              </CardBody>
            </Card>
          </Container>
        </div>
      
    </React.Fragment>
  );
};


export default withTranslation()(WeatherBuscar);
