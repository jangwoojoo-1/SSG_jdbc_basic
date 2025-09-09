/**
 * 이 패키지는 사용자 요청을 처리하고 모델(Model)과 뷰(View)를 연결하는 컨트롤러 클래스들을 포함합니다.
 * <p>
 * <strong>구성 요소:</strong>
 * <ul>
 *     <li>{@link Controller} - 관리 시스템에서 사용할 기본 동작(입력, 수정, 삭제, 검색 등)을 정의하는 인터페이스.</li>
 *     <li>{@link mvc_practice.controller.ControllerImpl} - {@code Controller}를 구현하며, 학생, 정규직 직원, 시간제 직원의 데이터를 관리하는 구체적인 기능을 제공합니다.</li>
 * </ul>
 * </p>
 *
 * <p>
 * 이 패키지의 목적은 사용자 요청(예: 데이터를 추가하거나 검색하는 작업 등)을 적절히 처리하여 모델과의 상호작용을 수행하고,
 * 그 결과를 뷰로 전달하는 것입니다. 이 과정에서 데이터를 관리하는 논리는 {@code ControllerImpl} 클래스에 포함되어 있습니다.
 * </p>
 *
 * <p>
 * <strong>주요 기능:</strong>
 * <ul>
 *     <li>관리 대상 설정: 학생, 정규직 직원, 시간제 직원 중 하나를 선택.</li>
 *     <li>데이터 관리: 데이터를 추가, 수정, 삭제, 검색.</li>
 *     <li>정렬/검색 옵션: 이름순, 번호순(학번/사번), 성적/실적순 정렬 등의 다양한 조건 제공.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>관련 패키지:</strong>
 * <ul>
 *     <li>{@link model} - 데이터베이스 접근 및 관리 작업을 수행.</li>
 *     <li>{@link vo} - 데이터 전송 객체(Value Objects)로, 모델과 컨트롤러 간의 데이터 교환에 사용.</li>
 *     <li>{@link view} - 사용자가 이 컨트롤러를 통해 작업 요청을 수행하는 CLI 기반의 인터페이스 제공.</li>
 * </ul>
 * </p>
 *
 * @author 서유미
 * @version 1.0
 * @since 2025.9.9.
 */

package mvc_practice.controller;