<%@page import="java.io.FilenameFilter"%>
<%@page import="java.util.Set"%>
<%@page import="java.io.File, java.io.FileNotFoundException, java.io.IOException, java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>AprioriMining - ServletDEMO</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>

</head>
<body>
	<div class="jumbotron text-center">
		<h1>APRIORI</h1>
		<p>Data Mining Algorithm for Frequent ItemSet Mining and Association Rule Learning</p>
	</div>
	
	<div class="container">
		<form id="form" action="Apriori" method="post" onsubmit="return checkForm();" style="margin: 0 0 10px 0;">
			<div class="panel panel-default">
	  			<div class="panel-heading"><b>Mining Option Panel</b></div>
	  			<div class="panel-body">
	  				<div class="row">	  				
			  			<div id="selection" class="col-sm-6">			
							<label for="sel1">Selecting Data Source:</label> 
							<select class="form-control" id="selOp" name="command" onchange="hideInputs()" style="margin-bottom: 10px">
								<option value="1">Learning Rules From DB</option>
								<option value="2">Reading Rules From File</option>
								<option value="3">Delete File From Server</option>
							</select>														
						</div>
						<div id="inputFields" class="col-sm-6">	
							<label id="lblParam">Input Parameters:</label>					
							<div id="divSelectFile" style="display: none;">								
								<select class="form-control" id="selFile" name="loadfile" style="margin-bottom: 10px;">								
									<option selected disabled hidden value="null">Choose file...</option>								
									<%  
										String a = getServletContext().getRealPath("/");
									
										FilenameFilter datFilter = new FilenameFilter(){
											public boolean accept(File dir, String filename){
												return filename.endsWith(".dat");
											}
										};
										
										File[] files = new File(a).listFiles(datFilter); 
										if(files != null && files.length != 0){
											for(File file : files){
												out.print("<option>" + file.getName() + "</option>");
											}
										}else
											out.print("<option disabled>No such file</option>");									
										%>								
								</select>
							</div>
							<div id="divInputData">								
								<input id="table" style="margin-bottom: 10px" class="form-control input-md" onkeypress="hideError('#table-error');" name="table" type="text"  placeholder="Data" />
								<div id="table-error" style="margin: 10px 0 30px 0; display:none;" class="alert alert-danger fade in"></div>
							</div>
							<div id="divInputMinSup">
								<input id="minSup" style="margin-bottom: 10px" class="form-control input-md" onkeypress="hideError('#minSup-error');" name="minSup" type="text" placeholder="minSup" />
								<div id="minSup-error" style="margin: 10px 0 30px 0; display:none;" class="alert alert-danger fade in"></div>
							</div>
							<div id="divInputMinConf">
								<input id="minConf" style="margin-bottom: 10px" class="form-control input-md" onkeypress="hideError('#minConf-error');" name="minConf" type="text" placeholder="minConf" />
								<div id="minConf-error" style="margin: 10px 0 30px 0; display:none;" class="alert alert-danger fade in"></div>
							</div>
							<div id="divInputFileName">	
								<label>Save file to:</label>	
								<input id="filename" class="form-control input-md" onkeypress="hideError('#filename-error');" name="filenamedata" type="text" placeholder="(e.g. Typing 'pattern' will be saved in pattern.dat)" />
								<div id="filename-error" style="margin: 10px 0 30px 0; display:none;" class="alert alert-danger fade in"></div>
							</div>							
						</div>
					</div>
				</div>	
			</div>
			<div class="row">
				<div class="col-sm-12">
					<button class="btn btn-primary btn-lg btn-block" type="submit">Execute</button>				
				</div>
			</div>
		</form>
		<div class="panel panel-default">
			<div class="panel-heading"><b>Patterns and Rules</b></div>
  			<div class="panel-body">
	  			<div class="col-sm-12">
		  			<div class="row" style="margin: 10px 0 30px 0;">
						<textarea id="res" style="resize: none;" readonly="readonly" name="result" class="form-control input-md" rows="8">
							<%  String result = (String) request.getAttribute("output");
								if(result != null)
									out.print(result);%>
						</textarea>
					</div>
					<div class="row" style="margin: 10px 0 0 0;">
						<textarea id="msg" style="resize: none;" readonly="readonly" name="message" class="form-control input-md" rows="3">
							<%  String message = (String) request.getAttribute("message");
								if(message != null)
									out.print(message);%>
						</textarea>
					</div>
				</div>
  			</div>
		</div>
		
	</div>

<script type="text/javascript">
	function hideError($id_name) {
		$($id_name).slideUp(100);
	}

	function hideInputs(){
		var command = document.getElementById('selOp').value;
		
		if(command == 1){
			$("#lblParam").html("Input Parameters:");
			$("#divSelectFile").hide();
			$("#divInputFileName").show();				
			$("#divInputData").show();
			$("#divInputMinSup").show();
			$("#divInputMinConf").show();
		}else{			
			if(command == 2)
				$("#lblParam").html("Select file to restore:");
			else
				$("#lblParam").html("Select file to delete:");
			
			hideError('#table-error');
			hideError('#minSup-error');
			hideError('#minConf-error');
			hideError('#filename-error');
			
			$("#divSelectFile").show();
			$("#divInputFileName").hide();
			$("#divInputData").hide();
			$("#divInputMinSup").hide();
			$("#divInputMinConf").hide();
		}
	}
	
	function checkForm() {
		document.getElementById('res').value = "";
		document.getElementById('msg').value = "";
		
		var onlyletters = /^[a-zA-Z]*$/;
		var control = true;
		
		var command = document.getElementById('selOp').value;
		var data = document.getElementById('table').value;
		var filename = document.getElementById('filename').value;
		var file_selected = document.getElementById('selFile').value;
		
		if(command == 1){
			var minsup = document.getElementById('minSup').value;
			var minconf = document.getElementById('minConf').value;
			
			if(data == ""){
				$("#table-error").slideDown(300);
				$("#table-error").html("<b>Warning!</b> Data field is empty!");
				control = false;
			}
			
			if(!onlyletters.test(data)){
				$("#table-error").slideDown(300);
				$("#table-error").html("<b>Warning!</b> Data field can only contains letters!");
				control = false;
			}
			
			if(minsup == ""){
				$("#minSup-error").html("<b>Warning!</b> minSup field can only contains letters!");
				$("#minSup-error").slideDown(300);
				control = false;	
			}
			else if(isNaN(minsup)){
				$("#minSup-error").html("<b>Warning!</b> minSup field can only contains numbers!");
				$("#minSup-error").slideDown(300);
				control = false;
			}
			else if((minsup<0) || (minsup>1)){
				$("#minSup-error").html("<b>Warning!</b> Illegal minSup field! Must be between 0 and 1.");
				$("#minSup-error").slideDown(300);
				control = false;
			}
	
			if(minconf == ""){
				$("#minConf-error").html("<b>Warning!</b> minConf field is empty!");
				$("#minConf-error").slideDown(300);
				control = false;
			}
			else if(isNaN(minconf)){
				$("#minConf-error").html("<b>Warning!</b> minConf field can only contains numbers!");
				$("#minConf-error").slideDown(300);
				control = false;
			}
			else if((minconf<0) || (minconf>1)){
				$("#minConf-error").html("<b>Warning!</b> Illegal minConf field! Must be between 0 and 1.");
				$("#minConf-error").slideDown(300);
				control = false;
			}
			
			if(filename == ""){
				$("#filename-error").html("<b>Warning!</b> Filename field is empty!");
				$("#filename-error").slideDown(300);
				control = false;
			}
			
			if(!onlyletters.test(filename)){
				$("#filename-error").html("<b>Warning!</b> Illegal filename. Chars not allowed in the field.");
				$("#filename-error").slideDown(300);
				control = false;
			}
		}else
			if(file_selected == 'null')
				control = false;		
					
		return control;
	}
	
</script>

</body>
</html>