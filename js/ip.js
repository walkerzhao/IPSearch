$(function(){
	$("#search").click(function(e){
		e.preventDefault();
		var ipValue=$("#ip").val();
		if(isIp(ipValue))
		{
			
			$.ajax({type:'POST',url:'ip.php',dataType:'json',data:$("#ip").val(),
			success:function(response,status,xhr){
				$('#showIp').text(ipValue);
				
				//后台返回的数据格式为json格式，也就是这里的response
				$("#showCountry").text(response.country);
				$("#showRegion").text(response.region);
				$("#showCity").text(response.city);
				$("#showIsp").text(response.isp);
				
				$("#ip").val('');	
				}
				});

		}
		else
		{
			alert("您输入的IP的地址不符合格式要求")
		}
		$("#ip").val('');
        
		
		
	}
		)
	
	
	
})



/*判断输入的IP地址是否合法*/
function isIp(ip)
{
	var checkIp=function(v){
		if(v<=255&&v>=0)
		return true;
		else 
		return false;
	}
	var result=ip.split('.');
	if(result.length==4)
	{
		return  checkIp(result[0])&&checkIp(result[1])&&checkIp(result[2])&&checkIp(result[3]);
	}
	else
	return false;
}




