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
const [selected, setSelected] = useState(false);
const [Temperatura,setTemperatura] = useState(0);

const fetchInformation = async (city,latAndLog) => {
  try {
    setLoading(true);
    const response = await post('/api/weather/v1/get',{city,latAndLog});
    setLoading(false);
    console.log("response")
    console.log(response)

    setTemperatura(response.averageTemperature)
    
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

    //fetchInitial();
  }, []);

  const fetchOptions = async () => {
    console.log('craziii')
    try {
      const responseList = await get('/api/weather/v1/cities');
      console.log('cidades')
      console.log(responseList)

      setCities(responseList);
    } catch (error) {
      
    }
  };

  const validation = useFormik({
    // enableReinitialize : use this flag when initial values needs to be changed
    enableReinitialize: true,
  
    initialValues: {
      latAndLog: '',
    },
    validationSchema: Yup.object({
      latAndLog: Yup.mixed().required("Please select an option"),
    }),
    onSubmit: (values) => {
      console.log('variavel submetida')
    
      
  }
  });

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


 function handleSelectCidades(e) {
  console.log('foi21')
  let latAndLog  = e.target.value;

  let city = cities.find(city => city.latAndLog === latAndLog);

  fetchInformation(city,latAndLog)
  setSelected(true)
  validation.setValues(prevValues => ({
    ...prevValues,
    latAndLog: latAndLog,
  }));
 }

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
                    
                    {selected ? (
                          <CardSubtitle className="font-14 text-muted">
                              Temperatura: {Temperatura}
                          </CardSubtitle>
                      ) : (
                          <CardSubtitle className="font-14 text-muted">
                              Temperatura ainda não selecionada
                          </CardSubtitle>
                    )}


                    <Form
                      className="form-horizontal"
                      onSubmit={(e) => {
                        e.preventDefault();
                        validation.handleSubmit();
                        return false;
                      }}
                    >
                    <div className="mb-3">
                      
                      <Input
                        name="latAndLog"
                        className="form-control"
                        placeholder="Digite o cidade da disciplina"
                        type="select"
                        onChange={handleSelectCidades}
                        onBlur={validation.handleBlur}
                        value={validation.values.latAndLog || ""}
                        invalid={validation.touched.latAndLog && validation.errors.latAndLog}
                      >
                        <option value="">Selecione</option>
                        {cities.map((option) => (
                        <option key={option.latAndLog} value={option.latAndLog}>
                          {option.name}
                        </option>
                        ))}
                        </Input>
                      {validation.touched.latAndLog && validation.errors.latAndLog ? (
                        <FormFeedback type="invalid">{validation.errors.latAndLog}</FormFeedback>
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
