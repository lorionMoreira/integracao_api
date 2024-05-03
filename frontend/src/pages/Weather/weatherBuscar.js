import React, {  useState,useMemo, useEffect  } from "react";
import {
  Container,
  Col,
  Row,
  Card,
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



const WeatherBuscar = props => {
  const { demoData } = useSelector(state => ({
    demoData: state.Login.demoData,
  }));

//console.log('componentes');
//console.log(demoData)
  const history = useNavigate();
  const dispatch = useDispatch();
  



const [loading, setLoading] = useState(false);


const fetchInitial = async (page) => {
  try {
    setLoading(true);
    const response = await get('/api/weather/get');

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
    const obj = JSON.parse(localStorage.getItem("authUser"));
    setAuthToken(obj?.token);

    fetchInitial();
  }, []);

  const handlePageChange = page => {
		fetchInitial(page-1);
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
              title={props.t("Componentes22")}
              breadcrumbItem={props.t("Buscar")}
            />
            <Card className="p-3">

            </Card>
          </Container>
        </div>
      
    </React.Fragment>
  );
};


export default withTranslation()(WeatherBuscar);
